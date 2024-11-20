package services

import utils.saveToFile
import utils.loadFromFile
import entities.Suscription
import entities.Service

class SuscriptionService {
    private val subscriptions = mutableListOf<Suscription>()

    init {
        subscriptions.addAll(loadFromFile("src/main/resources/data/suscriptions.json"))
    }

    fun viewSubscriptionsByService(serviceService: ServiceService) {
        println("Ingrese el ID del servicio para ver sus suscripciones:")
        val id = readLine()?.toIntOrNull()
        val service = serviceService.getServiceById(id ?: -1)
        if (service == null) {
            println("No se encontró un servicio con ese ID.")
            return
        }
        val serviceSubscriptions = subscriptions.filter { it.serviceId == id }  // Aquí se utiliza `serviceId`
        if (serviceSubscriptions.isEmpty()) {
            println("No hay suscripciones para el servicio ${service.name}.")
        } else {
            println("Suscripciones para el servicio ${service.name}:")
            serviceSubscriptions.forEach { println(it) }
        }
    }

    fun addSubscription(serviceService: ServiceService) {
        println("Ingrese el ID del servicio al que pertenece esta suscripción:")
        val serviceId = readLine()?.toIntOrNull()
        val service = serviceService.getServiceById(serviceId ?: -1)
        if (service == null) {
            println("No se encontró un servicio con ese ID.")
            return
        }
        val newId = if (subscriptions.isEmpty()) 1 else subscriptions.maxOf { it.id } + 1
        println("Ingrese el costo mensual:")
        val monthlyCost = readLine()?.toDoubleOrNull() ?: 0.0
        println("Ingrese la fecha de inicio (YYYY-MM-DD):")
        val startDate = readLine() ?: "0000-00-00"
        println("¿Renovación automática? (true/false):")
        val autoRenew = readLine()?.toBooleanStrictOrNull() ?: false
        val serviceName = readLine()?: "Servicio desconocido"
        val newSubscription = Suscription(newId, service.id, monthlyCost, startDate, autoRenew, serviceName)
        subscriptions.add(newSubscription)
        saveToFile(subscriptions, "src/main/resources/data/suscriptions.json")
        println("Suscripción creada: $newSubscription")
    }

    fun updateSubscription() {
        // Implementa la actualización
    }

    fun deleteSubscription() {
        // Implementa la eliminación
    }
}