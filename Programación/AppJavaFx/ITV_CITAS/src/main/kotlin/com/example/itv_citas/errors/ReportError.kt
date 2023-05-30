package com.example.itv_citas.errors

sealed class ReportError(val message: String) {
    class ExportError(type: String): ReportError("Error exporting $type report")
    class ImportError(type: String): ReportError("Error importing $type report")
    class ValidationError(type: String): ReportError("Error while validate report: $type")
}