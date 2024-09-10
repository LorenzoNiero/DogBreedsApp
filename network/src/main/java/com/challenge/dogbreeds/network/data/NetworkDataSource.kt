package com.challenge.dogbreeds.network.data

import com.challenge.dogbreeds.network.data.model.DogsNetwork


/**
 * Interface representing network calls to the DogApi backend
 */
interface NetworkDataSource {
    suspend fun getDogsWithSubBreeds(): DogsNetwork
}