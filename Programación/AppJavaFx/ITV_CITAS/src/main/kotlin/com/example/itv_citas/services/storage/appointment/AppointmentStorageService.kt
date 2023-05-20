package com.example.itv_citas.services.storage.appointment

import com.example.itv_citas.errors.AppointmentFileError
import com.example.itv_citas.models.Appointment
import com.example.itv_citas.services.storage.StorageService

interface AppointmentStorageService: StorageService<Appointment, AppointmentFileError>