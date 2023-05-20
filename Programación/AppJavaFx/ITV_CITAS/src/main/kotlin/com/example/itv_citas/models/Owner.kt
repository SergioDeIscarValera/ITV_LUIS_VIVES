package com.example.itv_citas.models

data class Owner(
    val ownerDNI: String,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val vehicles: List<Vehicle>
)
