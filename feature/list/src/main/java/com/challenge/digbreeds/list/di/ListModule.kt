package com.challenge.digbreeds.list.di

import com.challenge.digbreeds.list.domain.repository.DogRepository
import com.challenge.digbreeds.list.domain.repository.DogRepositoryImpl
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