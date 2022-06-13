package com.example.courierservicetest.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courierservicetest.ai.CarsFragment.CarsViewModel
import com.example.courierservicetest.ai.EditCarsFragment.EditCarViewModel
import com.example.courierservicetest.data.CarsDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarsViewModelFactory @Inject constructor (private val carsDatabase: CarsDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CarsViewModel::class.java) -> {
                CarsViewModel(carsDatabase) as T
            }
            modelClass.isAssignableFrom(EditCarViewModel::class.java) -> {
                EditCarViewModel(carsDatabase) as T
            }
            else -> throw IllegalStateException("Illegal ViewModel implementation")
        }
    }
}