package errors

sealed class StationError(val message: String) {
    object StationNotFound: StationError("Error Station not found")
}