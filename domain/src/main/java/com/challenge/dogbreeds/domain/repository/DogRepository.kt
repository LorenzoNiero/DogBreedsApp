package com.challenge.dogbreeds.domain.repository

import com.challenge.dogbreeds.common.domain.model.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    suspend fun fetchAllDogs()
    suspend fun fetchImageUrl(breedId: String, subBreedId: String?)
    fun observeAllDogs(): Flow<List<Dog>>
    fun enqueueFetchImageUrlWork(breedId: String, subBreedId: String?)
}


