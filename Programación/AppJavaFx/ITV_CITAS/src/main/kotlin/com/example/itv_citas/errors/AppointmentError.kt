package com.example.itv_citas.errors

sealed class AppointmentError(val message: String) {
    object AppointmentNotFound : AppointmentError("ERROR: Appointment not found")
    object AppointmentNotCreated : AppointmentError("ERROR: Appointment not created")
    object AppointmentNotUpdated : AppointmentError("ERROR: Appointment not updated")
    object AppointmentNotDeleted : AppointmentError("ERROR: Appointment not deleted")
}