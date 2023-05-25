package repositories.appointment

import errors.AppointmentError
import models.Appointment
import repositories.CrudRepository
import java.time.LocalDateTime

/**
 * Por la logica que hemos acordado, el campo más clave es la matrícula del vehículo por lo que no se puede actualizar y
 * la búsqueda se hará por ese campo.
 */
interface AppointmentRepository: CrudRepository<Appointment, String, AppointmentError> {
    fun findByIdEmployee(idEmployee: Long): List<Appointment>
    fun findByIdEmployee(idEmployee: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment>
    fun findByDate(startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment>
    fun findByDateAndCarNumber(carNumber: String, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment>
}