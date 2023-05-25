package mappers

import dto.EmployeeDto
import models.Employee

fun Employee.toDto(): EmployeeDto = EmployeeDto(
    id = id.toString(),
    name = name,
    email = email,
    userName = userName,
    phone = phone,
    password = password,
    specialty = specialty.toDto(),
    idStation = idEstacion.toString(),
    idResponsable = idResponsable.toString()
)

fun EmployeeDto.toClass(): Employee = Employee(
    id = id.toLong(),
    name = name,
    email = email,
    userName = userName,
    phone = phone,
    password = password,
    specialty = specialty.toClass(),
    idEstacion = idStation.toLong(),
    idResponsable = idResponsable.toLong()
)