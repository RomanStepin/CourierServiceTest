package com.example.courierservicetest.di

import com.example.courierservicetest.ai.CarsViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CarsDatabaseModule::class])
interface CarsViewModelFactoryComponent {
    fun getCarsViewModelFactory(): CarsViewModelFactory
}