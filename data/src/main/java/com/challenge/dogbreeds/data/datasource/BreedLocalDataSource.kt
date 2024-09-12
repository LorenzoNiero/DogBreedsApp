package com.challenge.dogbreeds.data.datasource

import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import kotlinx.coroutines.flow.Flow

interface BreedLocalDataSource {
    suspend fun deleteAllBreed()

    fun onBreedsUpdate(): Flow<List<BreedWithSubBreeds>>
    fun insertOrReplace(breedEntity: BreedEntity)
    suspend fun getBreed(id: String): BreedEntity?
}
