package com.challenge.digbreeds.list.domain.repository

import com.challenge.digbreeds.list.data.asExternalModel
import com.challenge.digbreeds.list.data.mapToDomainModel
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.entity.DogImageStatus
import com.challenge.dogbreeds.common.domain.entity.StatusImage
import com.challenge.dogbreeds.common.domain.entity.SubBreed
import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import com.challenge.dogbreeds.network.data.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val breedDao: BreedDao
) : DogRepository {

    override suspend fun fetchAllDogs() : List<Dog> = withContext(Dispatchers.IO) {
        val dogsNetwork = networkDataSource.fetchDogsWithSubBreeds()

        if (dogsNetwork.status == "success") {
            breedDao.deleteAll()

            //save to db
            dogsNetwork.message.forEach { dog ->
                breedDao.insert(BreedEntity(dog.key, dog.key, null, null))
                dog.value.forEach { subBreed ->
                    breedDao.insert(BreedEntity(subBreed, subBreed, null, dog.key))
                }
            }
        }

        return@withContext dogsNetwork.mapToDomainModel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAllDogs(): Flow<List<Dog>> = breedDao.observeAllBeerWithSubBreed().mapLatest { dog ->
        dog.map { it.asExternalModel() }
    }

    override suspend fun fetchImageUrl(breedId: String) : String = withContext(Dispatchers.IO) {
        val breedEntity = breedDao.getEntity(breedId)

        val urlImg = runCatching {
            val urlImg = networkDataSource.fetchImageDogRandom(breedId).message

            breedEntity?.also {
                breedEntity.urlImage = urlImg
                breedDao.insert(breedEntity)
            }

            return@runCatching urlImg
        }.getOrElse {
            return@getOrElse null
        }

        return@withContext urlImg ?: ""
    }

}

