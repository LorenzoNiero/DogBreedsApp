package com.challenge.digbreeds.list.domain.repository

import com.challenge.digbreeds.list.domain.entity.Dog

interface DogRepository {
    suspend fun getAllDogs(): List<Dog>
}


