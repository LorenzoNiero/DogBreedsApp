package com.challenge.dogbreeds.data.datasource

import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreedsRelation
import com.challenge.dogbreeds.database.model.SubBreedEntity
import kotlinx.coroutines.flow.Flow

interface BreedLocalDataSource {
    suspend fun deleteAll()

    fun onBreedsUpdate(): Flow<List<BreedWithSubBreedsRelation>>
    fun insertDogBreed(dog: DogBreedEntity)
    fun insertSubBreed(dog: SubBreedEntity)
    suspend fun getDogBreed(id: String): DogBreedEntity?
    suspend fun getSubBreed(id: String, parentBreedId: String ): SubBreedEntity?
}
