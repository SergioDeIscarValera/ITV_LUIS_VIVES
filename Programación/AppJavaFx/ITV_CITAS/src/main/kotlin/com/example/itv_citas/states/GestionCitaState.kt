package com.example.itv_citas.states


data class GestionCitaState(
    val matricula: String = "",
    val especialidad: List<String> = listOf(),
    val trabajador: List<String> = listOf(),
    val fecha: String = "",
    val hora: List<String> = listOf(),
    val especialidadSelected: String? = "",
    val trabajadorSelected: String? = "",
    val horaSelected: String? = "",
    val typeAction: TypeAction = TypeAction.BASE
)