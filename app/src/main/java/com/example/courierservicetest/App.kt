package com.example.courierservicetest

import android.app.Activity
import android.app.Application
import com.example.courierservicetest.di.CarsViewModelFactoryComponent
import com.example.courierservicetest.di.DaggerCarsViewModelFactoryComponent

class App: Application() {
    companion object {
        lateinit var instance: App
        val carsViewModelFactoryComponent = DaggerCarsViewModelFactoryComponent.create()
    }

    init {
        instance = this
    }
}