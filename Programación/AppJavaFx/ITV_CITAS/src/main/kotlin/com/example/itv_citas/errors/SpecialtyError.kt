package com.example.itv_citas.errors

sealed class SpecialtyError(val message: String) {
    object SpecialtyNotFound: SpecialtyError("ERROR: Specialty not found")
}