package services.storage.appointment

import services.storage.StorageService
import errors.AppointmentFileError
import models.Appointment

interface AppointmentStorageService: StorageService<Appointment, AppointmentFileError>