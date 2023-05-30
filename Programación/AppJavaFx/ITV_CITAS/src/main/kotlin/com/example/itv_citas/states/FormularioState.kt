package com.example.itv_citas.states

data class FormularioState(
    val fecha: String = "",
    val vehiculo: String = "",
    val propietario: String = "",
    val trabajador: String  = "",
    val frenado: String = "",
    val contaminacion: String = "",
    val interior: Boolean = false,
    val luces: Boolean = false,
    val apto: Boolean = false
)