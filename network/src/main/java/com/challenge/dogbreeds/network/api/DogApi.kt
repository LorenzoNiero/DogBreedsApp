package com.challenge.dogbreeds.network.api

import com.challenge.dogbreeds.network.data.model.DogsNetwork
import com.challenge.dogbreeds.network.data.model.ImageNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Implementation of endpoint
 * @see <a href="https://dog.ceo/dog-api/documentation/">Documentation</a>
 *
 */
interface DogApi {

    /**
     * Get all breeds of dogs
     */
    @GET("breeds/list/all")
    suspend fun getAllBreeds() : DogsNetwork

    /**
     * Gets a random image of a dog
     * @param id
     */
    @GET("breed/{breed}/images/random")
    suspend fun getBreedImage(@Path("breed") breedId: String) : ImageNetwork

}