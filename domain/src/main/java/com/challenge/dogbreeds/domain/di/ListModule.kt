package com.challenge.dogbreeds.domain.di

import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.repository.DogRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class ListModule {
    @Provides
    @Singleton
    fun provideHomeRepository(repository: DogRepositoryImpl): DogRepository = repository
}