package com.challenge.dogbreeds.network.data

import com.challenge.dogbreeds.network.data.model.DogNetwork


/**
 * Interface representing network calls to the DogApi backend
 */
interface NetworkDataSource {
    suspend fun getDogsWithSubBreeds(): List<DogNetwork>
}