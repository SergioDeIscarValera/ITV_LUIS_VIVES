package com.example.itv_citas.dto

data class OwnerDto(
    val ownerDNI: String,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val vehicles: List<VehicleDto>
)
