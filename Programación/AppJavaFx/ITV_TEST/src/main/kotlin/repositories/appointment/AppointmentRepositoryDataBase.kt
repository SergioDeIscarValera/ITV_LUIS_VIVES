package repositories.appointment

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import errors.AppointmentError
import models.Appointment
import mu.KotlinLogging
import services.database.DataBaseManager
import services.database.DataBaseManager.dataBase
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

class AppointmentRepositoryDataBase: AppointmentRepository {

    override fun findByIdEmployee(idEmployee: Long): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByIdEmployee" }
        return findAll().filter { it.idEmployee == idEmployee }
    }

    override fun findByIdEmployee(idEmployee: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByIdEmployee" }
        return findByIdEmployee(idEmployee).filter { it.date in startDate..endDate }
    }

    override fun findByDate(startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByDate" }
        return findAll().filter { it.date in startDate..endDate }
    }

    override fun findByDateAndCarNumber(carNumber: String, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByDate" }
        return findByDate(startDate, endDate).filter { it.carNumber == carNumber }
    }

    override fun findAll(): Iterable<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindAll" }
        val appointments = mutableListOf<Appointment>()
        val sql = """SELECT * FROM tCitas"""
        dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                appointments.add(
                    resultToAppointment(result)
                )
            }
        }
        return appointments.toList()
    }

    override fun findById(id: String): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindById" }
        var appointment: Appointment? = null
        val sql = """SELECT * FROM tCitas WHERE cMatricula = ?"""
        dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id)
            val result = stm.executeQuery()
            if (result.next()){
                appointment = resultToAppointment(result)
            }
        }
        return if (appointment != null) Ok(appointment!!) else Err(AppointmentError.AppointmentNotFound)
    }

    private fun resultToAppointment(result: ResultSet) = Appointment(
        result.getLong("nId_Trabajador"),
        result.getString("cMatricula"),
        result.getTimestamp("dFecha_Citacion").toLocalDateTime()
    )

    override fun save(element: Appointment): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tsave" }
        return if (existsById(element.carNumber)){
            update(element)
        }else{
            create(element)
        }
    }

    private fun create(element: Appointment): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tcreate" }
        var result: Int
        val sql = """INSERT INTO tCitas (nId_Trabajador, cMatricula, dFecha_Citacion) VALUES (?, ?, ?)"""
        dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, element.idEmployee)
            stm.setString(2, element.carNumber)
            stm.setTimestamp(3, Timestamp.valueOf(element.date))
            result = stm.executeUpdate()
        }
        return if (result == 0) Err(AppointmentError.AppointmentNotCreated) else Ok(element)
    }

    private fun update(element: Appointment): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tupdate" }
        var result: Int
        val sql = """UPDATE tCitas SET nId_Trabajador = ?, dFecha_Citacion = ? WHERE cMatricula = ?"""
        dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, element.idEmployee)
            stm.setTimestamp(2, Timestamp.valueOf(element.date))
            stm.setString(3, element.carNumber)
            result = stm.executeUpdate()
        }
        return if (result == 1) Ok(element) else Err(AppointmentError.AppointmentNotUpdated)
    }

    override fun saveAll(elements: Iterable<Appointment>) {
        logger.debug { "AppointmentRepositoryDataBase ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: String): Result<Boolean, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tdeleteById" }
        var result: Int
        val sql = """DELETE FROM tCitas WHERE cMatricula = ?"""
        dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id)
            result = stm.executeUpdate()
        }
        return if (result == 1) Ok(true) else Err(AppointmentError.AppointmentNotDeleted)
    }

    override fun delete(element: Appointment): Result<Boolean, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tdelete" }
        return deleteById(element.carNumber)
    }

    override fun deleteAll() {
        logger.debug { "AppointmentRepositoryDataBase ->\tdeleteAll" }
        val sql = """DELETE FROM tCitas"""
        dataBase.prepareStatement(sql).use { stm ->
            stm.executeUpdate()
        }
    }

    override fun existsById(id: String): Boolean {
        logger.debug { "AppointmentRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        return findAll().count()
    }
}