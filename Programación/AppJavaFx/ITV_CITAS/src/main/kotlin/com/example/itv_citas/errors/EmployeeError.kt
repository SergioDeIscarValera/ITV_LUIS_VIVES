package com.example.itv_citas.errors

sealed class EmployeeError(val message: String) {
    object EmployeeNotFound: EmployeeError("Error Employee not found")
    object EmployeeNotSaved: EmployeeError("Error Employee not saved")
    object EmployeeNotUpdate: EmployeeError("Error Employee not updated")

    class ExportError(type: String): EmployeeError("Error exporting $type report")
    class ImportError(type: String): EmployeeError("Error importing $type report")
}