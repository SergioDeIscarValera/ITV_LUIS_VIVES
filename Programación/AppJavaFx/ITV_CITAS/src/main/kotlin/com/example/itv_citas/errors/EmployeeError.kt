package com.example.itv_citas.errors

sealed class EmployeeError(val message: String) {
    object EmployeeNotFound: EmployeeError("ERROR: Employee not found")

    class ExportError(type: String): EmployeeError("Error exporting $type report")
    class ImportError(type: String): EmployeeError("Error importing $type report")
}