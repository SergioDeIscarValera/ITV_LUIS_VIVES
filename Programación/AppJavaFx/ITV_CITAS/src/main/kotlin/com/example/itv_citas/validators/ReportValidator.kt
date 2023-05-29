package com.example.itv_citas.validators

import com.example.itv_citas.dto.ReportDto
import com.example.itv_citas.errors.ReportError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

fun ReportDto.validar(): Result<ReportDto, ReportError> {
    require(this.id.isNotBlank()) { return Err(ReportError.ValidationError("El id no puede estar vacío.")) }
    require(this.braking.toDouble() in 1.0..10.0) { return Err(ReportError.ValidationError("Frenado debe estar comprendido entre 1.0 y 10.0")) }
    require(this.pollution.toDouble() in 20.0..50.0) { return Err(ReportError.ValidationError("Contaminación debe estar comprendido entre 20.0 y 50.0")) }
    return Ok(this)
}