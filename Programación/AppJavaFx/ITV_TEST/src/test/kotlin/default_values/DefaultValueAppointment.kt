package default_values

import models.Appointment
import java.time.LocalDateTime

object DefaultValueAppointment {
    fun defaultValueAppointment() = listOf(
        Appointment(2, "2385KLP", LocalDateTime.parse("2023-06-01T08:49:04")),
        Appointment(3, "5421GVJ", LocalDateTime.parse("2023-06-06T14:45:51")),
        Appointment(4, "8796QWT", LocalDateTime.parse("2023-06-23T12:14:52")),
    )
}