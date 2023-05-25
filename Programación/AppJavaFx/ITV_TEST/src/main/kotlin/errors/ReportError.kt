package errors

sealed class ReportError(val message: String) {
    class ExportError(type: String): ReportError("Error exporting $type report")
    class ImportError(type: String): ReportError("Error importing $type report")
}