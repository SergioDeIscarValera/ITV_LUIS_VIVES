package models

import models.enums.TypeMotor
import models.enums.TypeVehicle
import java.time.LocalDate
import java.time.LocalDateTime

data class Vehicle (
    val carNumber: String,
    val brand: String,
    val model: String,
    val type: TypeVehicle,
    val motor: TypeMotor,
    val matriculationDate: LocalDate,
    val lastItvDate: LocalDateTime,
    val dniOwner: String
)