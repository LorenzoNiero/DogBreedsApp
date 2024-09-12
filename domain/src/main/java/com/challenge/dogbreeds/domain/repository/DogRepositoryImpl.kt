package com.challenge.dogbreeds.domain.repository

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.entity.DogImageStatus
import com.challenge.dogbreeds.common.domain.entity.StatusImage
import com.challenge.dogbreeds.data.datasource.BreedLocalDataSource
import com.challenge.dogbreeds.data.mapper.asExternalModel
import com.challenge.dogbreeds.data.mapper.mapToDomainModel
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.ImageEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity
import com.challenge.dogbreeds.domain.worker.ImageDownloadWorker
import com.challenge.dogbreeds.domain.worker.WorkConstraints
import com.challenge.dogbreeds.network.data.NetworkDataSource
import com.challenge.dogbreeds.network.data.model.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val localDataSource: BreedLocalDataSource,
    private val networkDataSource: NetworkDataSource,
    private val workManager : WorkManager
) : DogRepository {

    override suspend fun fetchAllDogs() = withContext(Dispatchers.IO) {
        val dogsNetwork = networkDataSource.fetchDogsWithSubBreeds()

        if (dogsNetwork.status == StatusResponse.SUCCESS) {
            localDataSource.deleteAll()

            val dogs = runCatching {
                dogsNetwork.mapToDomainModel()
            }.getOrElse { it ->
                Log.e("DogRepositoryImpl", "fetchAllDogs: $it")
                null
            }


            //save to db
            dogs?.forEach { dog ->
                localDataSource.insertDogBreed(
                    DogBreedEntity(
                    id = dog.id,
                    name = dog.name,
//                    subBreeds = dog.subBreeds.map { it.id }
                ))
                dog.subBreeds.forEach { subBreed ->
                    localDataSource.insertSubBreed(
                        SubBreedEntity(
                            breadId = subBreed.id,
                            name = subBreed.name,
                            parentBreed = dog.id
                        )
                    )
                }
            }
        }
    }

    override suspend fun fetchImageUrl(breedId: String) {
        withContext(Dispatchers.IO) {
            val imageEntity = localDataSource.getImage(breedId) ?: ImageEntity(id = breedId, urlImage = null, statusFailImage = null)
            val urlImg = runCatching { networkDataSource.fetchImageDogRandom(breedId).message }

            imageEntity.also {
                imageEntity.urlImage = urlImg.getOrNull()
                imageEntity.statusFailImage = urlImg.isFailure
                localDataSource.insertImage(imageEntity)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllDogs(): Flow<List<Dog>> =
        localDataSource.onBreedsUpdate().mapLatest { dog ->
            dog.map { it.asExternalModel() }
        }.combine(localDataSource.onImagesUpdate()) { dogs, images ->
            dogs.map { dog ->
                dog.copy(
                    image = images.firstOrNull { image -> image.id == dog.id }?.asExternalModel()
                        ?: DogImageStatus(null, StatusImage.NONE),
                    subBreeds = dog.subBreeds.map { subBreed ->
                        subBreed.copy(
                            image = images.firstOrNull { image -> image.id == subBreed.id }
                                ?.asExternalModel() ?: DogImageStatus(null, StatusImage.NONE),
                        )
                    }
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllImages(): Flow<List<DogImageStatus>> = localDataSource.onImagesUpdate().mapLatest { dog ->
        dog.map { it.asExternalModel() }
    }

    override fun enqueueFetchImageUrlWork (breedId: String){
        val work = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(WorkConstraints)
            .setInputData(workDataOf(ImageDownloadWorker.KEY_ID_STRING to breedId))
            .setInitialDelay(0L, TimeUnit.SECONDS)
            .addTag(breedId)
            .build()

        workManager.enqueue(
            work
        )
    }

}

fun ImageEntity.asExternalModel() = DogImageStatus(this.urlImage,
    when{
        this.urlImage != null -> {
            StatusImage.SUCCESS
        }
        this.statusFailImage == true -> {
            StatusImage.ERROR
        }
        else -> {
            StatusImage.NONE
        }
    })



