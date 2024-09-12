package com.challenge.dogbreeds.domain.repository

import com.challenge.dogbreeds.common.domain.entity.Dog
import kotlinx.coroutines.flow.Flow

interface DogRepository {
    suspend fun fetchAllDogs(): List<Dog>
    suspend fun fetchImageUrl(breedId: String) : String
    fun observeAllDogs(): Flow<List<Dog>>
}


