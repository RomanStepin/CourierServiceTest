package com.example.courierservicetest.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/**
 * Класс автомобиля, он же используется для хранения в базе
 * @param imageUri Uri выбранной картинки, при сохранении в базу заменяется путем к копии картинки
 */
@Parcelize
@Entity(tableName = "cars")
data class Car(@PrimaryKey(autoGenerate = true) var primaryKey: Long, var model: String, var brand: String, var imageUri: String) :
    Parcelable
