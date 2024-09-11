package com.challenge.dogbreeds.database.dao

import androidx.room.Dao
import com.challenge.dogbreeds.database.dao.base.BaseDao
import com.challenge.dogbreeds.database.model.BreedEntity


@Dao
abstract class BreedDao : BaseDao<BreedEntity>(BreedEntity.TABLE_NAME)  {

}