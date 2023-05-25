package mappers

import dto.SpecialtyDto
import models.Specialty

fun Specialty.toDto(): SpecialtyDto = SpecialtyDto(
    nombre = nombre,
    salario = salario.toString()
)

fun SpecialtyDto.toClass(): Specialty = Specialty(
    nombre = nombre,
    salario = salario.toInt()
)