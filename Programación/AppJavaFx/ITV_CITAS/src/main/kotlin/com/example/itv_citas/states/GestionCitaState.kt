package com.example.itv_citas.states


data class GestionCitaState(
    val matricula: String = "",
    val especialidades: List<String> = listOf(),
    val trabajadores: List<String> = listOf(),
    val fecha: String = "",
    val hora: List<String> = listOf(),
    val trabajadorSelected: String = "",
    val horaSelected: String = "",
    val typeAction: TypeActionView = TypeActionView.NEUTRAL
)