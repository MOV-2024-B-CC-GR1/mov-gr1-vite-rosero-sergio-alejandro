import services.ServiceService
import services.SuscriptionService
//Codigo creado por Sergio Vite
fun main() {
    val serviceService = ServiceService()
    val suscriptionService = SuscriptionService()

    do {
        println(
            """
            [----------Menú Principal-----------]
            |-----------------------------------|
            |            Servicios              |
            |-----------------------------------|
            |1. Ver servicios                   |
            |2. Agregar servicio                |
            |3. Actualizar servicio             |
            |4. Eliminar servicio               |
            |-----------------------------------|
            |          Suscripciones            |
            |-----------------------------------|
            |5. Ver suscripciones por servicio  |
            |6. Agregar suscripción             |
            |7. Actualizar suscripción          |
            |8. Eliminar suscripción            |
            |9. Salir                           |
            |___________________________________|
            -Seleccione una opción:
        """.trimIndent()
        )
        val option = readLine()?.toIntOrNull()

        when (option) {
            1 -> serviceService.viewServices()
            2 -> serviceService.addService()
            3 -> serviceService.updateService()
            4 -> serviceService.deleteService()
            5 -> suscriptionService.viewSubscriptionsByService(serviceService)
            6 -> suscriptionService.addSubscription(serviceService)
            7 -> suscriptionService.updateSubscription()
            8 -> suscriptionService.deleteSubscription()
            9 -> println("Saliendo...")
            else -> println("Opción no válida.")
        }
    } while (option != 9)
}
