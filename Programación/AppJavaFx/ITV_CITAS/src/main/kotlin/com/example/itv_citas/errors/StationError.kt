package com.example.itv_citas.errors

sealed class StationError(val message: String) {
    object StationNotFound: StationError("ERROR: Station not found")
}