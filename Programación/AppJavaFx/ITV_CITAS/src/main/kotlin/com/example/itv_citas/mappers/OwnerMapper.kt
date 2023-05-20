package com.example.itv_citas.mappers

import com.example.itv_citas.dto.OwnerDto
import com.example.itv_citas.models.Owner

fun Owner.toDto(): OwnerDto = OwnerDto(
    ownerDNI = ownerDNI,
    name = name,
    lastName = lastName,
    email = email,
    phone = phone,
    vehicles = vehicles.map { it.toDto() }
)

fun OwnerDto.toClass(): Owner = Owner(
    ownerDNI = ownerDNI,
    name = name,
    lastName = lastName,
    email = email,
    phone = phone,
    vehicles = vehicles.map { it.toClass() }
)