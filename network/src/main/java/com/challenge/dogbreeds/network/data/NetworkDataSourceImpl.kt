package com.challenge.dogbreeds.network.data

import com.challenge.dogbreeds.network.BuildConfig
import com.challenge.dogbreeds.network.api.DogApi
import com.challenge.dogbreeds.network.data.model.DogsNetwork
import com.challenge.dogbreeds.network.data.model.ImageNetwork
import com.squareup.moshi.Moshi
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

private const val API_BASE_URL = BuildConfig.BASE_URL

class RetrofitNetwork @Inject constructor(
    okhttpCallFactory: Call.Factory,
    moshi : Moshi
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            MoshiConverterFactory.create(
                moshi
            )
        )
        .build()

    private val dogApi = networkApi.create(DogApi::class.java)

    override suspend fun fetchDogsWithSubBreeds(): DogsNetwork = dogApi.getAllBreeds()
    override suspend fun fetchImageDogRandom(breedId: String): ImageNetwork = dogApi.getBreedImage(breedId)

}