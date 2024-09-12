package com.challenge.dogbreeds.domain.repository

import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.challenge.dogbreeds.common.domain.model.Dog
import com.challenge.dogbreeds.data.datasource.BreedLocalDataSource
import com.challenge.dogbreeds.data.mapper.asExternalModel
import com.challenge.dogbreeds.data.mapper.mapToDomainModel
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity
import com.challenge.dogbreeds.domain.worker.ImageDownloadWorker
import com.challenge.dogbreeds.domain.worker.WorkConstraints
import com.challenge.dogbreeds.network.data.NetworkDataSource
import com.challenge.dogbreeds.network.data.model.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
                    name = dog.name
                ))
                dog.subBreeds.forEach { subBreed ->
                    localDataSource.insertSubBreed(
                        SubBreedEntity(
                            id = subBreed.id,
                            parentBreedId = dog.id,
                            name = subBreed.name,
                        )
                    )
                }
            }
        }
    }

    override suspend fun fetchImageUrl(breedId: String, subBreedId: String?) {
        withContext(Dispatchers.IO) {
            if (subBreedId != null) {
                fetchImageSubBreedUrl(breedId, subBreedId)
            }
            else{
                fetchImageDogBreedUrl(breedId)
            }


        }
    }

    private suspend fun fetchImageSubBreedUrl(breedId: String, subBreedId: String) {
        val imageEntity = localDataSource.getSubBreed(subBreedId, breedId)
        val urlImg = runCatching { networkDataSource.fetchImageSubBreedRandom(breedId,subBreedId).message }

        imageEntity?.also {
            imageEntity.image.urlImage = urlImg.getOrNull()
            imageEntity.image.statusFailImage = urlImg.isFailure
            localDataSource.insertSubBreed(imageEntity)
        }
    }

    private suspend fun fetchImageDogBreedUrl(breedId: String) {
        val imageEntity = localDataSource.getDogBreed(breedId)
        val urlImg = runCatching { networkDataSource.fetchImageDogRandom(breedId).message }

        imageEntity?.also {
            imageEntity.image.urlImage = urlImg.getOrNull()
            imageEntity.image.statusFailImage = urlImg.isFailure
            localDataSource.insertDogBreed(imageEntity)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllDogs(): Flow<List<Dog>> =
        localDataSource.onBreedsUpdate().mapLatest { dog ->
            dog.map { it.asExternalModel() }
        }

    override fun enqueueFetchImageUrlWork (breedId: String, subBreedId: String?) {
        val work = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(WorkConstraints)
            .setInputData(workDataOf(
                ImageDownloadWorker.KEY_BREAD_ID_STRING to breedId,
                ImageDownloadWorker.KEY_SUB_BREAD_ID_STRING to subBreedId,
                )
            )
            .setInitialDelay(0L, TimeUnit.SECONDS)
            .addTag(breedId)
            .build()

        workManager.enqueue(
            work
        )
    }

}