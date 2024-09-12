package com.challenge.dogbreeds.domain.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.data.datasource.BreedLocalDataSource
import com.challenge.dogbreeds.data.mapper.asExternalModel
import com.challenge.dogbreeds.data.mapper.mapToDomainModel
import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.domain.worker.ImageDownloadWorker
import com.challenge.dogbreeds.domain.worker.WorkConstraints
import com.challenge.dogbreeds.network.data.NetworkDataSource
import com.challenge.dogbreeds.network.data.model.StatusResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataSource: BreedLocalDataSource,
    private val networkDataSource: NetworkDataSource,
) : DogRepository {

    override suspend fun fetchAllDogs() = withContext(Dispatchers.IO) {
        val dogsNetwork = networkDataSource.fetchDogsWithSubBreeds()

        if (dogsNetwork.status == StatusResponse.SUCCESS) {
            localDataSource.deleteAllBreed()

            val dogs = dogsNetwork.mapToDomainModel()

            //save to db
            dogs.forEach { dog ->
                localDataSource.insertOrReplace(
                    BreedEntity(
                    id = dog.id,
                    name = dog.name,
                    urlImage = dog.image.imageUrl,
                    mainBreed = null
                ))
                dog.subBreeds.forEach { subBreed ->
                    localDataSource.insertOrReplace( BreedEntity(
                        id = subBreed.id,
                        name = subBreed.name,
                        urlImage = subBreed.image.imageUrl,
                        mainBreed = dog.id
                    ))
                }
            }
        }
    }

    override suspend fun fetchImageUrl(breedId: String) {
        withContext(Dispatchers.IO) {
            val breedEntity = localDataSource.getBreed(breedId)
            val urlImg = networkDataSource.fetchImageDogRandom(breedId).message

            breedEntity?.also {
                breedEntity.urlImage = urlImg
                localDataSource.insertOrReplace(breedEntity)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllDogs(): Flow<List<Dog>> = localDataSource.onBreedsUpdate().mapLatest { dog ->
        dog.map { it.asExternalModel() }
    }

    override fun enqueueFetchImageUrlWork (breedId: String){
        val workManager = WorkManager.getInstance(context)

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



