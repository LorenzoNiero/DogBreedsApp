package com.challenge.dogbreeds.database.dao

import android.media.Image
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.challenge.dogbreeds.database.dao.base.BaseDao
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import com.challenge.dogbreeds.database.model.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ImageDao : BaseDao<ImageEntity>(ImageEntity.TABLE_NAME)  {

    @Query(value = "DELETE FROM image")
    abstract suspend fun deleteAll()

    @Transaction
    @Query(value = "SELECT * FROM image")
    abstract fun observeAllImage(): Flow<List<ImageEntity>>
}
