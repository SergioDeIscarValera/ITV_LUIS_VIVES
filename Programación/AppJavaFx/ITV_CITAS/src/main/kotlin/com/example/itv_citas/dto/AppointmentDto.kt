package com.example.itv_citas.dto

data class AppointmentDto(
    val idAppointment: Long,
    val idEmployee: Long,
    val carNumber: String,
    val date: String
)
