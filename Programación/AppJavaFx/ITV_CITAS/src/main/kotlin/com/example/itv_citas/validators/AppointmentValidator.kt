package com.example.itv_citas.validators

import com.example.itv_citas.dto.AppointmentDto
import com.example.itv_citas.errors.AppointmentError
import com.example.itv_citas.models.Appointment
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

val matriculaRegex = Regex("^[0-9]{4}[B-DF-HJ-NP-TV-Z]{3}$")
val dateFormat = Regex("""^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}$""")

fun AppointmentDto.validate(): Result<AppointmentDto, AppointmentError> {
    return when{
        !carNumber.matches(matriculaRegex) -> Err(AppointmentError.AppointmentCarNumberInvalid)
        !date.matches(dateFormat) -> Err(AppointmentError.AppointmentDateInvalid)
        else -> Ok(this)
    }
}

fun Appointment.validate(appointments: List<Appointment>): Result<Appointment, AppointmentError>{
    // No más de 8 citas en su intervalo de 30 minutos
    // El empleado no puede tener más de 4 cutas en su intervalo de 30 minutos
    return when{
        appointments.size >= 8 -> Err(AppointmentError.AppointmentMaxAppointments)
        appointments.filter { it.idEmployee == this.idEmployee }.size >= 4 -> Err(AppointmentError.AppointmentMaxAppointmentsEmployee)
        else -> Ok(this)
    }
}