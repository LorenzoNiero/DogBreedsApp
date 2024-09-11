package com.challenge.dogbreeds.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.challenge.dogbreeds.database.dao.base.BaseDao
import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import kotlinx.coroutines.flow.Flow


@Dao
abstract class BreedDao : BaseDao<BreedEntity>(BreedEntity.TABLE_NAME)  {

    @Query(value = "SELECT * FROM breed order by name")
    abstract fun observeAllBeer(): Flow<List<BreedEntity>>

    @Query(value = "DELETE FROM breed")
    abstract suspend fun deleteAll()

    @Transaction
    @Query(value = "SELECT * FROM breed order by name")
    abstract fun observeAllBeerWithSubBreed(): Flow<List<BreedWithSubBreeds>>

}