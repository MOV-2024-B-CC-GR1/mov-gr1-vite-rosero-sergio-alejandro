package com.example.a02_deber

import java.time.LocalDate

data class Cliente(
    var id: Long,
    var nombre: String,
    var superficie: Double,
    var es_independiente: Boolean,
    var fecha_fundacion: LocalDate,
    val ciudades: MutableList<Servicio> = mutableListOf()
)
