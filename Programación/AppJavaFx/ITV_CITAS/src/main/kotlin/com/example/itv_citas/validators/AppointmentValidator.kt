package com.example.itv_citas.validators

import com.example.itv_citas.dto.AppointmentDto
import com.example.itv_citas.errors.AppointmentError
import com.example.itv_citas.models.Appointment
import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.time.DateTimeException
import java.time.LocalDateTime

val matriculaRegex = Regex("^[0-9]{4}[B-DF-HJ-NP-TV-Z]{3}$")
val fechaRegex = Regex("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])T(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9])$")

fun AppointmentDto.validar(): Result<AppointmentDto, AppointmentError> {
    require(this.carNumber.matches(matriculaRegex)) {
        showErrorAlert("Matrícula no válida", "La matrícula no tiene un formato válido (0000XXX)")
        return Err(AppointmentError.AppointmentNotValid) }
    require(this.date.matches(fechaRegex)) {
        showErrorAlert("Fecha no válida", "Debe introducir una fecha")
        return Err(AppointmentError.AppointmentNotValid) }
    return Ok(this)
}