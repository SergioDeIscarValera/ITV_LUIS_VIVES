package storage.appointment

import config.AppConfig
import default_values.DefaultValueAppointment
import default_values.DefaultValueAppointment.defaultValueAppointment
import errors.AppointmentFileError
import models.Appointment
import services.storage.appointment.AppointmentJsonStorage
import storage.StorageGenericTest
import java.io.File
import java.time.LocalDateTime

class AppointmentJsonTest: StorageGenericTest<Appointment, AppointmentFileError>() {
    private val appConfig = AppConfig()
    override fun filePath() = "${appConfig.appData}${File.separator}appointments.json"

    override fun getStorage() = AppointmentJsonStorage()

    override fun getListaDefault() = defaultValueAppointment()

    override fun getPath() = appConfig.appData
}