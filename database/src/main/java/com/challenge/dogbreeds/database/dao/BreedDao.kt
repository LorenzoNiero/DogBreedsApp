package com.challenge.dogbreeds.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.challenge.dogbreeds.database.dao.base.BaseDao
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreedsRelation
import kotlinx.coroutines.flow.Flow


@Dao
abstract class BreedDao : BaseDao<DogBreedEntity>(DogBreedEntity.TABLE_NAME)  {

    @Query(value = "SELECT * FROM breed order by name")
    abstract fun observeAllBreed(): Flow<List<DogBreedEntity>>

    @Query(value = "DELETE FROM breed")
    abstract suspend fun deleteAll()

    @Transaction
    @Query(value = "SELECT * FROM breed order by name")
    abstract fun observeAllDogWithSubBreed(): Flow<List<BreedWithSubBreedsRelation>>

}