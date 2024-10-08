package com.challenge.dogbreeds.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.dao.SubBreedDao
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity


const val VERSION_DATABASE = 1

@Database(
    entities = [
        DogBreedEntity::class,
        SubBreedEntity::class
    ],
    version = VERSION_DATABASE,
    autoMigrations = [
//         AutoMigration (from = 1, to = VERSION_DATABASE),
                ],
    exportSchema = true,
)

@TypeConverters(
    //todo: add converters
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun subBreedDao(): SubBreedDao
}





