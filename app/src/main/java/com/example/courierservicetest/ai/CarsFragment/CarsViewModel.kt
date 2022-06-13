package com.example.courierservicetest.ai.CarsFragment

import androidx.lifecycle.ViewModel
import com.example.courierservicetest.data.CarsDatabase
import com.example.courierservicetest.models.Car

class CarsViewModel(private val carsDatabase: CarsDatabase) : ViewModel() {
    suspend fun getCars(query: String): List<Car> {
        return try {
            carsDatabase.getCarsDao().getCars(query)
        } catch (e: Exception) {
            listOf()
        }
    }

    suspend fun deleteCar(car: Car): Int {
        return try {
            carsDatabase.getCarsDao().deleteCar(car)
        } catch (e: Exception) {
            0
        }
    }
}