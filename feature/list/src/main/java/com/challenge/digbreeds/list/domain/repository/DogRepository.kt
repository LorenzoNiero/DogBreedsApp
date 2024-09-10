package com.challenge.digbreeds.list.domain.repository

import com.challenge.dogbreeds.common.domain.entity.Dog

interface DogRepository {
    suspend fun fetchAllDogs(): List<Dog>
    suspend fun fetchImageUrl(breedId: String) : String
}


