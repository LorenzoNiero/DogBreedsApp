package com.challenge.dogbreeds.database.di

import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.AppDatabase
import com.challenge.dogbreeds.database.dao.ImageDao
import com.challenge.dogbreeds.database.dao.SubBreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesBreedDao(
        database: AppDatabase
    ): BreedDao = database.breedDao()

    @Provides
    fun providesSubBreedDao(
        database: AppDatabase
    ): SubBreedDao = database.subBreedDao()


    @Provides
    fun providesImageDao(
        database: AppDatabase
    ): ImageDao = database.imageDao()


}