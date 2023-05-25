package com.example.itv_citas.services.storage.appointment

import errors.AppointmentFileError
import models.Appointment
import com.example.itv_citas.services.storage.StorageService

interface AppointmentStorageService: StorageService<Appointment, AppointmentFileError>