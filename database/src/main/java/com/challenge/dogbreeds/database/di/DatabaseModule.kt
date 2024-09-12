package com.challenge.dogbreeds.database.di


import android.content.Context
import androidx.room.Room
import com.challenge.dogbreeds.database.dao.BreedDao
import com.challenge.dogbreeds.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ModuleDatabase {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "database"
        ).build()
    }
}


class DatabaseContentProvider {
    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface DatabaseContentProviderEntryPoint {
        fun breedDao(): BreedDao
    }
}