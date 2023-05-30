package mappers

import dto.AppointmentDto
import models.Appointment
import java.time.LocalDateTime

fun Appointment.toDto(): AppointmentDto = AppointmentDto(
    idEmployee = this.idEmployee,
    carNumber = this.carNumber,
    date = this.date.toString()
)

fun AppointmentDto.toClass(): Appointment = Appointment(
    idEmployee = this.idEmployee,
    carNumber = this.carNumber,
    date = LocalDateTime.parse(this.date.replace(" ", "T"))
)