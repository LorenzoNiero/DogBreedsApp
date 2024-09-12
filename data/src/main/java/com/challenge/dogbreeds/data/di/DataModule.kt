package com.challenge.dogbreeds.data.di

import com.challenge.dogbreeds.data.datasource.BreedLocalDataSource
import com.challenge.dogbreeds.data.datasource.BreedLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DataModule {

    @Provides
    @Singleton
    fun provideBreedLocalDataSource(localDataSourceImpl: BreedLocalDataSourceImpl): BreedLocalDataSource = localDataSourceImpl
}