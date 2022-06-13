package com.example.courierservicetest.ai.EditCarsFragment

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.courierservicetest.data.CarsDatabase
import com.example.courierservicetest.deleteFileWithPath
import com.example.courierservicetest.models.Car
import com.example.courierservicetest.saveFileInFilesDirectory
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.*

class EditCarViewModel(private val carsDatabase: CarsDatabase) : ViewModel() {

    val selectedImageUriFlow: MutableSharedFlow<Uri> = MutableSharedFlow(1)
    var selectedImageUri: Uri = Uri.EMPTY

    suspend fun changeSelectedImageUri(uri: Uri) {
        this.selectedImageUri = uri
        selectedImageUriFlow.emit(uri)
    }

    private suspend fun addCar(car: Car): Long {
        return try {
            carsDatabase.getCarsDao().addCar(car)
        } catch (e: Exception) {
            0
        }
    }

    private suspend fun updateCar(car: Car): Int {
        return try {
            carsDatabase.getCarsDao().updateCar(car)
        } catch (e: Exception) {
            0
        }
    }

    suspend fun saveEditableCar(initialCar: Car, newCarModel: String, newCarBrand: String) {
        var savedCarImageFilePath = initialCar.imageUri
        if (selectedImageUri != Uri.EMPTY) {
            deleteFileWithPath(initialCar.imageUri)
            savedCarImageFilePath = saveFileInFilesDirectory(
                selectedImageUri,
                "car_image_id_${initialCar.primaryKey}_${Calendar.getInstance().timeInMillis}"
            )
        }
        val savedCar =
            Car(initialCar.primaryKey, newCarModel, newCarBrand, savedCarImageFilePath)
        updateCar(savedCar)
    }

    suspend fun saveNewCar(carModel: String, carBrand: String) {
        val savedCar = Car(0, carModel, carBrand, "")
        val savedCarId = addCar(savedCar)
        if (selectedImageUri != Uri.EMPTY && savedCarId != 0L) {
            savedCar.primaryKey = savedCarId
            val savedCarImageName =
                "car_image_id_${savedCarId}_${Calendar.getInstance().timeInMillis}"
            val path = saveFileInFilesDirectory(selectedImageUri, savedCarImageName)
            savedCar.imageUri = path
            updateCar(savedCar)
        }
    }
}