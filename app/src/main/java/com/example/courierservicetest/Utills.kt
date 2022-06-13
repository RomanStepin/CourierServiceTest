package com.example.courierservicetest

import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import java.io.*

/**
 * Метод для копирования файла в filesDir приложения
 * @param originalFileUri Uri копируемого файла
 * @param savedFileName Имя для копии файла
 * @return путь к копии файла
 */
fun saveFileInFilesDirectory(originalFileUri: Uri, savedFileName: String): String {
    val savedImageFile = File(App.instance.filesDir, savedFileName)

    val inputStream: InputStream? = App.instance.contentResolver.openInputStream(originalFileUri)
    val outputStream = FileOutputStream(savedImageFile)

    inputStream?.let {
        copyInputStreamToOutputStream(inputStream, outputStream)
    }

    return savedImageFile.path
}

@Throws(IOException::class)
fun copyInputStreamToOutputStream(inputStream: InputStream, outputStream: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (inputStream.read(buf).also { length = it } > 0) {
        outputStream.write(buf, 0, length)
    }
    inputStream.close()
    outputStream.close()
}

fun deleteFileWithPath(realPath: String) {
    val removableFile = File(realPath)
    removableFile.delete()
}