package entities

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Int,
    var name: String,
    var type: String
)
