package com.example.itv_citas.mappers

import com.example.itv_citas.dto.AppointmentDto
import com.example.itv_citas.models.Appointment
import java.time.LocalDateTime

fun Appointment.toDto(): AppointmentDto = AppointmentDto(
    idAppointment = this.idAppointment,
    idEmployee = this.idEmployee,
    carNumber = this.carNumber,
    date = this.date.toString()
)

fun AppointmentDto.toModel(): Appointment = Appointment(
    idAppointment = this.idAppointment,
    idEmployee = this.idEmployee,
    carNumber = this.carNumber,
    date = LocalDateTime.parse(this.date)
)