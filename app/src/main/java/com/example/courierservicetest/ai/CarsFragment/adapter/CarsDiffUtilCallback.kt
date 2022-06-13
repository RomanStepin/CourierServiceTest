package com.example.courierservicetest.ai.CarsFragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.courierservicetest.models.Car

class CarsDiffUtilCallback(val oldCarsList: List<Car>, val newCarsList: List<Car>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldCarsList.size
    }

    override fun getNewListSize(): Int {
        return newCarsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCarsList[oldItemPosition].primaryKey == newCarsList[newItemPosition].primaryKey
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCarsList[oldItemPosition] == newCarsList[newItemPosition]
    }
}