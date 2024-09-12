package com.challenge.dogbreeds.data.datasource

import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import com.challenge.dogbreeds.database.model.ImageEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity
import kotlinx.coroutines.flow.Flow

interface BreedLocalDataSource {
    suspend fun deleteAll()

    fun onBreedsUpdate(): Flow<List<BreedWithSubBreeds>>
    fun insertDogBreed(dog: DogBreedEntity)
    fun insertSubBreed(dog: SubBreedEntity)

    fun onImagesUpdate(): Flow<List<ImageEntity>>
    suspend fun getImage(id: String): ImageEntity?

    fun insertImage(dog: ImageEntity)
}
