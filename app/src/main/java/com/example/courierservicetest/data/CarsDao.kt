package com.example.courierservicetest.data

import androidx.room.*
import com.example.courierservicetest.models.Car

@Dao
interface CarsDao {
    @Query("SELECT * FROM CARS WHERE LOWER(MODEL) LIKE '%' || LOWER(:query) || '%' ORDER BY BRAND")
    suspend fun getCars(query: String): List<Car>

    @Insert
    suspend fun addCar(car: Car): Long

    @Delete
    suspend fun deleteCar(car: Car): Int

    @Update
    suspend fun updateCar(car: Car): Int
}