package utils

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

// Función para guardar datos en un archivo
inline fun <reified T> saveToFile(data: List<T>, fileName: String) {
    val jsonString = Json.encodeToString(data)
    File(fileName).writeText(jsonString)
}

// Función para cargar datos desde un archivo
inline fun <reified T> loadFromFile(fileName: String): List<T> {
    val file = File(fileName)
    if (!file.exists()) return emptyList() // Si no existe, retorna lista vacía
    val jsonString = file.readText()
    return Json.decodeFromString(jsonString)
}
