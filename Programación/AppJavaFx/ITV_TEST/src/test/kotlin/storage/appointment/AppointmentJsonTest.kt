package storage.appointment

import config.AppConfig
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

    override fun getListaDefault() = listOf(
        Appointment(2, "2385KLP", LocalDateTime.parse("2023-06-01T08:49:04")),
        Appointment(3, "5421GVJ", LocalDateTime.parse("2023-06-06T14:45:51")),
        Appointment(4, "8796QWT", LocalDateTime.parse("2023-06-23T12:14:52")),
    )

    override fun getPath() = appConfig.appData
}