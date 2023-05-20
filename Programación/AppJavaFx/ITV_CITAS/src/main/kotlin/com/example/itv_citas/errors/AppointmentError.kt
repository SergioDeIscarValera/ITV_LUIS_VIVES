package com.example.itv_citas.errors

sealed class AppointmentError(val message: String) {
    object AppointmentNotFound : AppointmentError("Error Appointment not found")
    object AppointmentNotCreated : AppointmentError("Error Appointment not created")
    object AppointmentNotUpdated : AppointmentError("Error Appointment not updated")
    object AppointmentNotDeleted : AppointmentError("Error Appointment not deleted")
}