package errors

sealed class SpecialtyError(val message: String) {
    object SpecialtyNotFound: SpecialtyError("Error Specialty not found")
}