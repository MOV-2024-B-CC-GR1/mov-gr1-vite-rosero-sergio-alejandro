package entities

import kotlinx.serialization.Serializable

@Serializable
data class Suscription(
    val id: Int,            // Identificador de la suscripción
    val serviceId: Int,     // ID del servicio al que pertenece la suscripción
    val monthlyCost: Double, // Costo mensual
    val startDate: String,   // Fecha de inicio (formato YYYY-MM-DD)
    val autoRenew: Boolean,   // Renovación automática (t = si/f = no)
    val serviceName: String
)

