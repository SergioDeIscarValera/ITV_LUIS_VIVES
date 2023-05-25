package mappers

import dto.OwnerDto
import models.Owner

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