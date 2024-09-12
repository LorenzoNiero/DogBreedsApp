package com.challenge.dogbreeds.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.challenge.dogbreeds.database.dao.base.IBaseDao
import com.challenge.dogbreeds.database.model.SubBreedEntity

@Dao
abstract class SubBreedDao : IBaseDao<SubBreedEntity>  {

    @Query(value = "DELETE FROM subBreed")
    abstract suspend fun deleteAll()

}
