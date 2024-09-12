package com.challenge.dogbreeds.data.datasource

import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.dao.SubBreedDao
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreedsRelation
import com.challenge.dogbreeds.database.model.SubBreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedLocalDataSourceImpl @Inject constructor(
    private val dogBreedDao: BreedDao,
    private val subBreedDao: SubBreedDao
) : BreedLocalDataSource {

    override fun onBreedsUpdate(): Flow<List<BreedWithSubBreedsRelation>> = dogBreedDao.observeAllDogWithSubBreed()

    override fun insertDogBreed(dog: DogBreedEntity) {
        dogBreedDao.insert(dog)
    }

    override fun insertSubBreed(dog: SubBreedEntity) {
        subBreedDao.insert(dog)
    }

    override suspend fun getDogBreed(id: String): DogBreedEntity? = dogBreedDao.getEntity(id)

    override suspend fun getSubBreed(id: String, parentBreedId: String): SubBreedEntity? =
        subBreedDao.getEntity(id, parentBreedId)

    override suspend fun deleteAll() {
        dogBreedDao.deleteAll()
        subBreedDao.deleteAll()
    }
}