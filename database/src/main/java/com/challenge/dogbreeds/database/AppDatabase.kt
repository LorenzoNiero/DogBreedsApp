package com.challenge.dogbreeds.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.model.BreedEntity


const val VERSION_DATABASE = 2

@Database(
    entities = [
        BreedEntity::class,
    ],
    version = VERSION_DATABASE,
    autoMigrations = [
         AutoMigration (from = 1, to = VERSION_DATABASE),
                ],
    exportSchema = true,
)

@TypeConverters(
    //todo
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}





