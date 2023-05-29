package com.example.itv_citas.states

import com.example.itv_citas.models.Appointment

data class CitaState(
    val citas: List<Appointment> = listOf(),
    val tipos: List<String> = listOf(),

    val fechaAppointment: String = "",
    val idTrabajador: String = "",

    val matriculaVehicle: String = "",
    val marcaVehicle: String = "",
    val tipoVehicle: String = "",
    val fechaMatriculacionVehicle: String = "",
    val modeloVehicle: String = "",
    val motorVehicle: String = "",
    val fehcaUltimaRevisionVehicle: String = "",

    val dniOwner: String = "",
    val nombreOwner: String = "",
    val apellidoOwner: String = "",
    val telefonoOwner: String = "",
    val correoOwner: String = "",
    val typeAction: TypeActionView = TypeActionView.NEUTRAL
)


