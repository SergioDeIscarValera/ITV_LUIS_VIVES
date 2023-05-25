package errors

sealed class AppointmentFileError(val message: String) {
    class ExportError(type: String): AppointmentFileError("Error exporting $type report")
    class ImportError(type: String): AppointmentFileError("Error importing $type report")
}