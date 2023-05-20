package com.example.itv_citas.errors

sealed class EmployeeError(val message: String) {
    object EmployeeNotFound: EmployeeError("ERROR: Employee not found")
}