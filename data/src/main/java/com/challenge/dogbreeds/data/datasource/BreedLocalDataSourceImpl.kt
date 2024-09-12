package com.challenge.dogbreeds.data.datasource

import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.dao.ImageDao
import com.challenge.dogbreeds.database.dao.SubBreedDao
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import com.challenge.dogbreeds.database.model.ImageEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedLocalDataSourceImpl @Inject constructor(
    private val dogBreedDao: BreedDao,
    private val subBreedDao: SubBreedDao,
    private val imageDao: ImageDao
) : BreedLocalDataSource {

    override fun onBreedsUpdate(): Flow<List<BreedWithSubBreeds>> = dogBreedDao.observeAllBeerWithSubBreed()

    override fun insertDogBreed(dog: DogBreedEntity) {
        dogBreedDao.insert(dog)
    }

    override fun insertSubBreed(dog: SubBreedEntity) {
        subBreedDao.insert(dog)
    }

    override fun onImagesUpdate(): Flow<List<ImageEntity>> {
        return imageDao.observeAllImage()
    }

    override fun insertImage(dog: ImageEntity) {
        imageDao.insert(dog)
    }

    override suspend fun getImage(id: String): ImageEntity? {
        return imageDao.getEntity(id)
    }

    override suspend fun deleteAll() {
        dogBreedDao.deleteAll()
        subBreedDao.deleteAll()
        imageDao.deleteAll()
    }
}