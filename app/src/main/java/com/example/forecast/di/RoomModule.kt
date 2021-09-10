package com.example.forecast.di

import javax.inject.Singleton

import androidx.room.Room

import android.app.Application
import com.example.forecast.data.db.AppDatabase
import com.example.forecast.data.db.LocationDao
import com.example.forecast.data.db.LocationRepository
import com.example.forecast.data.db.LocationRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RoomModule(application: Application?) {
    private var locationsDatabase: AppDatabase

    init {
        locationsDatabase =
            Room.databaseBuilder(application!!, AppDatabase::class.java, "locations-db").build()
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return locationsDatabase
    }

    @Singleton
    @Provides
    fun providesLocationDao(locationsDatabase: AppDatabase): LocationDao {
        return locationsDatabase.locationDao()
    }

    @Singleton
    @Provides
    fun locationsRepository(locationDao: LocationDao): LocationRepository {
        return LocationRepositoryImpl(locationDao)
    }
}