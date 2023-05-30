package errors

sealed class OwnerError(val message: String) {
    object OwnerNotFound: OwnerError("Error Owner not found")
}