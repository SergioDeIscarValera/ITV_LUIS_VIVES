package com.example.itv_citas.mappers

import com.example.itv_citas.dto.SpecialtyDto
import com.example.itv_citas.models.Specialty

fun Specialty.toDto(): SpecialtyDto = SpecialtyDto(
    nombre = nombre,
    salario = salario.toString()
)

fun SpecialtyDto.toClass(): Specialty = Specialty(
    nombre = nombre,
    salario = salario.toInt()
)