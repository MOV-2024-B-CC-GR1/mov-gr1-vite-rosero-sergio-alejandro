package com.example.ccgr12024b_gasm.model

/**
 * Representa un pedido realizado por un cliente en el sistema.
 *
 * @property id Identificador único del pedido (autogenerado en la base de datos).
 * @property cliente_id ID del cliente al que está asociado este pedido.
 * @property descripcion Descripción detallada del pedido (por ejemplo, productos o servicios solicitados).
 * @property estado Estado actual del pedido (por ejemplo: "Pendiente", "Completado", "Cancelado").
 * @property fechaPedido Fecha en la que se realizó el pedido, almacenada como String (compatible con SQLite).
 */
data class Servicios(
    val id: Int = 0, // Identificador único del pedido
    var cliente_id: Int, // ID del cliente al que pertenece el pedido
    var descripcion: String, // Descripción del pedido
    var estado: String, // Estado del pedido (por ejemplo: "Pendiente", "Completado")
    var fechaPedido: String // Fecha del pedido (en formato String para SQLite)
)
