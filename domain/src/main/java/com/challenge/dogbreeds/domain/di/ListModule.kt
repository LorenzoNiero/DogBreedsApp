package com.challenge.dogbreeds.domain.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.repository.DogRepositoryImpl
import com.challenge.dogbreeds.domain.worker.ImageDownloadWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.io.path.name
import kotlin.jvm.java

@InstallIn(SingletonComponent::class)
@Module
internal class ListModule {
    @Provides
    @Singleton
    fun provideHomeRepository(repository: DogRepositoryImpl): DogRepository = repository
}