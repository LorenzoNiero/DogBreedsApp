package com.challenge.dogbreeds.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.dao.ImageDao
import com.challenge.dogbreeds.database.dao.SubBreedDao
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.ImageEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity


const val VERSION_DATABASE = 1

@Database(
    entities = [
        DogBreedEntity::class,
        SubBreedEntity::class,
        ImageEntity::class
    ],
    version = VERSION_DATABASE,
    autoMigrations = [
//         AutoMigration (from = 1, to = 2),
//         AutoMigration (from = 2, to = 3),
//         AutoMigration (from = 3, to = VERSION_DATABASE),
                ],
    exportSchema = true,
)

@TypeConverters(
    //todo
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun subBreedDao(): SubBreedDao
    abstract fun imageDao(): ImageDao
}





