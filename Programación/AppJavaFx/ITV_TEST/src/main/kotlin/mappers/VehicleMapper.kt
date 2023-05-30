package mappers

import dto.VehicleDto
import models.Vehicle
import models.enums.TypeMotor
import models.enums.TypeVehicle
import java.time.LocalDate
import java.time.LocalDateTime

fun Vehicle.toDto(): VehicleDto = VehicleDto(
    carNumber = carNumber,
    brand = brand,
    model = model,
    type = type.toString(),
    motor = motor.toString(),
    matriculationDate = matriculationDate.toString(),
    lastItvDate = lastItvDate.toString(),
    dniOwner = dniOwner
)

fun VehicleDto.toClass(): Vehicle = Vehicle(
    carNumber = carNumber,
    brand = brand,
    model = model,
    type = TypeVehicle.valueOf(type),
    motor = TypeMotor.valueOf(motor),
    matriculationDate = LocalDate.parse(matriculationDate),
    lastItvDate = LocalDateTime.parse(lastItvDate.replace(" ", "T")),
    dniOwner = dniOwner
)