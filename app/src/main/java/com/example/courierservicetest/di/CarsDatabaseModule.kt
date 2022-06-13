package com.example.courierservicetest.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.courierservicetest.App
import com.example.courierservicetest.data.CarsDatabase
import dagger.Module
import dagger.Provides

@Module
class CarsDatabaseModule {
    @Provides
    fun getCarsDatabase(): CarsDatabase {
       return Room
           .databaseBuilder(App.instance, CarsDatabase::class.java, "carsDatabase")
           .build()
    }
}