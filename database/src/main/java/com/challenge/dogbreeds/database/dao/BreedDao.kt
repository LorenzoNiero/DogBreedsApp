package com.challenge.dogbreeds.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.challenge.dogbreeds.database.dao.base.BaseDao
import com.challenge.dogbreeds.database.model.BreedEntity
import kotlinx.coroutines.flow.Flow


@Dao
abstract class BreedDao : BaseDao<BreedEntity>(BreedEntity.TABLE_NAME)  {

    @Query(value = "SELECT * FROM breed order by name")
    abstract fun observeAllBeer(): Flow<List<BreedEntity>>

    @Query(value = "DELETE FROM breed")
    abstract suspend fun deleteAll()


}