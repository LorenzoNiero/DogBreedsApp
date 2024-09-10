package com.challenge.digbreeds.list.domain.repository

import com.challenge.digbreeds.list.data.mapToDomainModel
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.network.data.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) : DogRepository {

    override suspend fun getAllDogs() : List<Dog> = withContext(Dispatchers.IO) {
        networkDataSource.getDogsWithSubBreeds().mapToDomainModel()
    }

}