package com.example.itv_citas.mappers

import com.example.itv_citas.dto.ReportDto
import com.example.itv_citas.models.Report

fun Report.toDto(): ReportDto = ReportDto(
    id = id.toString(),
    favorable = favorable.toString(),
    braking = braking.toString(),
    pollution = pollution.toString(),
    inside = inside.toString(),
    lights = lights.toString(),
    employee = employee.toDto(),
    vehicle = vehicle.toDto(),
    owner = owner.toDto()
)

fun ReportDto.toClass(): Report = Report(
    id = id.toLong(),
    favorable = favorable.toBoolean(),
    braking = braking.toDouble(),
    pollution = pollution.toDouble(),
    inside = inside.toBoolean(),
    lights = lights.toBoolean(),
    employee = employee.toClass(),
    vehicle = vehicle.toClass(),
    owner = owner.toClass()
)