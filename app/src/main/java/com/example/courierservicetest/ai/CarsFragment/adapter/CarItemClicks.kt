package com.example.courierservicetest.ai.CarsFragment.adapter

import com.example.courierservicetest.models.Car

interface CarItemClicks {
    fun onImageClickListener(imageUri: String)
    fun onEditClickListener (car: Car)
    fun onDeleteClickListener (car: Car)
}