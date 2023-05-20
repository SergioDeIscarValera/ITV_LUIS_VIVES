package com.example.itv_citas.errors

sealed class OwnerError(val message: String) {
    object OwnerNotFound: OwnerError("ERROR: Owner not found")
}