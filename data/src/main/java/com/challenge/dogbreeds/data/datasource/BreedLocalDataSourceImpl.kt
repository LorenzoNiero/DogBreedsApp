package com.challenge.dogbreeds.data.datasource

import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedLocalDataSourceImpl @Inject constructor(
    private val dao: BreedDao
) : BreedLocalDataSource {

    override fun onBreedsUpdate(): Flow<List<BreedWithSubBreeds>> = dao.observeAllBeerWithSubBreed()

    override fun insertOrReplace(breedEntity: BreedEntity) {
        dao.insert(breedEntity)
    }

    override suspend fun getBreed(id: String): BreedEntity? {
        return dao.getEntity(id)
    }

    override suspend fun deleteAllBreed() {
        dao.deleteAll()
    }
}