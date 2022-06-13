package com.example.courierservicetest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.courierservicetest.models.Car

@Database(entities = [Car::class], version = 1)
public abstract class CarsDatabase: RoomDatabase() {
    public abstract fun getCarsDao(): CarsDao
}