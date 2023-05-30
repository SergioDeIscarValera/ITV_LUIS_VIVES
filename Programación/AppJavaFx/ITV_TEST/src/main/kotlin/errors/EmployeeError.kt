package errors

sealed class EmployeeError(val message: String) {
    object EmployeeNotFound: EmployeeError("Error Employee not found")
    object EmployeeNotSaved: EmployeeError("Error Employee not saved")
    object EmployeeNotUpdate: EmployeeError("Error Employee not updated")
    object UserNotFound: EmployeeError("Error User not found")
    object InvalidPassword: EmployeeError("Password is not correct")

    class ExportError(type: String): EmployeeError("Error exporting $type report")
    class ImportError(type: String): EmployeeError("Error importing $type report")
}