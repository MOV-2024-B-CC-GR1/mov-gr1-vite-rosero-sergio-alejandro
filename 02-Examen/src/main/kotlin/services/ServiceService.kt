package services

import utils.loadFromFile
import utils.saveToFile
import entities.Service

class ServiceService {
    private val services = mutableListOf<Service>()

    init {
        services.addAll(loadFromFile("src/main/resources/data/services.json"))
    }

    fun viewServices() {
        if (services.isEmpty()) {
            println("No hay servicios registrados.")
        } else {
            services.forEach { println(it) }
        }
    }

    fun addService() {
        val newId = if (services.isEmpty()) 1 else services.maxOf { it.id } + 1
        println("Ingrese el nombre del servicio:")
        val name = readLine() ?: "Servicio Desconocido"
        println("Ingrese el tipo de servicio:")
        val type = readLine() ?: "Tipo desconocido"
        val newService = Service(id = newId, name = name, type = type)
        services.add(newService)
        saveToFile(services, "src/main/resources/data/services.json")
        println("Servicio agregado: $newService")
    }

    fun updateService() {
        println("Ingrese el ID del servicio que desea actualizar:")
        val id = readLine()?.toIntOrNull()
        val service = services.find { it.id == id }
        if (service == null) {
            println("No se encontró un servicio con ese ID.")
            return
        }
        println("Actualizar datos para el servicio: $service")
        println("Ingrese el nuevo nombre (actual: ${service.name}):")
        service.name = readLine() ?: service.name
        println("Ingrese el nuevo tipo (actual: ${service.type}):")
        service.type = readLine() ?: service.type
        saveToFile(services, "src/main/resources/data/services.json")
        println("Servicio actualizado: $service")
    }

    fun deleteService() {
        println("Ingrese el ID del servicio que desea eliminar:")
        val id = readLine()?.toIntOrNull()
        val service = services.find { it.id == id }
        if (service == null) {
            println("No se encontró un servicio con ese ID.")
            return
        }
        services.remove(service)
        saveToFile(services, "src/main/resources/data/services.json")
        println("Servicio eliminado: $service")
    }

    fun getServiceById(id: Int): Service? = services.find { it.id == id }
}
