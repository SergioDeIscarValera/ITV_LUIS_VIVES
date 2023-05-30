package models

import java.time.LocalDateTime

data class Appointment(
    val idEmployee: Long,
    val carNumber: String,
    val date: LocalDateTime
)
