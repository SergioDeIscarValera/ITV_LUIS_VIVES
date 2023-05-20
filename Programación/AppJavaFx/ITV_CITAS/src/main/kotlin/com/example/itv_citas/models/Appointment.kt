package com.example.itv_citas.models

import java.time.LocalDateTime

data class Appointment(
    val idAppointment: Long = 0,
    val idEmployee: Long,
    val carNumber: String,
    val date: LocalDateTime
)
