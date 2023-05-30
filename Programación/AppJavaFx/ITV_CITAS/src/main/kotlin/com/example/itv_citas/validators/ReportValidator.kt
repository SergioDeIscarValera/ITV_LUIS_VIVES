package com.example.itv_citas.validators

import com.example.itv_citas.dto.ReportDto
import com.example.itv_citas.errors.ReportError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

fun ReportDto.validate(): Result<ReportDto, ReportError> {
    return when{
        id.isBlank() -> Err(ReportError.ValidationError("El id no puede estar vacío."))
        braking.toDouble() !in 1.0..10.0 -> Err(ReportError.ValidationError("Frenado debe estar comprendido entre 1.0 y 10.0"))
        pollution.toDouble() !in 20.0..50.0 -> Err(ReportError.ValidationError("Contaminación debe estar comprendido entre 20.0 y 50.0"))
        else -> Ok(this)
    }
}