package com.example.itv_citas.errors

sealed class VehicleError(val message: String) {
    object VehicleNotFound: VehicleError("Error Vehicle not found")
}