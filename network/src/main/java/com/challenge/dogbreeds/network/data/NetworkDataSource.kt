package com.challenge.dogbreeds.network.data

import com.challenge.dogbreeds.network.data.model.DogsNetwork
import com.challenge.dogbreeds.network.data.model.ImageNetwork


/**
 * Interface representing network calls to the DogApi backend
 */
interface NetworkDataSource {
    suspend fun fetchDogsWithSubBreeds(): DogsNetwork
    suspend fun fetchImageDogRandom(breedId : String): ImageNetwork
}