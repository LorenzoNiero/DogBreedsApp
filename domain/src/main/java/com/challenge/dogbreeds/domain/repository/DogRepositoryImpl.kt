package com.challenge.dogbreeds.domain.repository

import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.data.datasource.BreedLocalDataSource
import com.challenge.dogbreeds.data.mapper.asExternalModel
import com.challenge.dogbreeds.data.mapper.mapToDomainModel
import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.network.data.NetworkDataSource
import com.challenge.dogbreeds.network.data.model.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val localDataSource: BreedLocalDataSource,
    private val networkDataSource: NetworkDataSource,
) : DogRepository {

    override suspend fun fetchAllDogs() : List<Dog> = withContext(Dispatchers.IO) {
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

        return@withContext dogsNetwork.mapToDomainModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllDogs(): Flow<List<Dog>> = localDataSource.onBreedsUpdate().mapLatest { dog ->
        dog.map { it.asExternalModel() }
    }

    override suspend fun fetchImageUrl(breedId: String) : String = withContext(Dispatchers.IO) {
        val breedEntity = localDataSource.getBreed(breedId)

        val urlImg = runCatching {
            val urlImg = networkDataSource.fetchImageDogRandom(breedId).message

            breedEntity?.also {
                breedEntity.urlImage = urlImg
                localDataSource.insertOrReplace(breedEntity)
            }

            return@runCatching urlImg
        }.getOrElse {
            return@getOrElse null
        }

        return@withContext urlImg ?: ""
    }

}



