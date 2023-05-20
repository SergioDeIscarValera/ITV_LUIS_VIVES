package com.example.itv_citas.repositories.appointment

import com.example.itv_citas.errors.AppointmentError
import com.example.itv_citas.models.Appointment
import com.example.itv_citas.services.database.DataBaseManager
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Timestamp
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

class AppointmentRepositoryDataBase: AppointmentRepository, KoinComponent {
    private val dataBaseManager: DataBaseManager by inject()

    override fun findByIdEmployee(idEmployee: Long): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByIdEmployee" }
        return findAll().filter { it.idEmployee == idEmployee }
    }

    override fun findByIdEmployee(idEmployee: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByIdEmployee" }
        return findByIdEmployee(idEmployee).filter { it.date in startDate..endDate }
    }

    override fun findByCarNumber(carNumber: String): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByCarNumber" }
        return findAll().filter { it.carNumber == carNumber }
    }

    override fun findByDate(carNumber: String, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindByDate" }
        return findAll().filter { it.date in startDate..endDate }
    }

    override fun findAll(): Iterable<Appointment> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindAll" }
        val appointments = mutableListOf<Appointment>()
        val sql = """SELECT * FROM tAppointment"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                appointments.add(
                    resultToAppointment(result)
                )
            }
        }
        return appointments.toList()
    }

    override fun findById(id: Long): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tfindById" }
        var appointment: Appointment? = null
        val sql = """SELECT * FROM tAppointment WHERE nIdAppointment = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            if (result.next()){
                appointment = resultToAppointment(result)
            }
        }
        return if (appointment != null) Ok(appointment!!) else Err(AppointmentError.AppointmentNotFound)
    }

    private fun resultToAppointment(result: ResultSet) = Appointment(
        result.getLong("nIdAppointment"),
        result.getLong("nIdEmployee"),
        result.getString("cCarNumber"),
        result.getTimestamp("dDate").toLocalDateTime()
    )

    override fun save(element: Appointment): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tsave" }
        return if (existsById(element.idAppointment)){
            update(element)
        }else{
            create(element)
        }
    }

    private fun create(element: Appointment): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tcreate" }
        var newId = 0L
        val sql = """INSERT INTO tAppointment (nIdEmployee, cCarNumber, dDate) VALUES (?, ?, ?)"""
        dataBaseManager.dataBase.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setLong(1, element.idEmployee)
                stm.setString(2, element.carNumber)
                stm.setTimestamp(3, Timestamp.valueOf(element.date))
                stm.executeUpdate()
                val result = stm.generatedKeys
                if (result.next()){
                    newId = result.getLong(1)
                }
            }
        }
        return if (newId == 0L) Err(AppointmentError.AppointmentNotCreated) else Ok(element.copy(idAppointment = newId))
    }

    private fun update(element: Appointment): Result<Appointment, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tupdate" }
        var result: Int
        val sql = """UPDATE tAppointment SET nIdEmployee = ?, cCarNumber = ?, dDate = ? WHERE nIdAppointment = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, element.idEmployee)
            stm.setString(2, element.carNumber)
            stm.setTimestamp(3, Timestamp.valueOf(element.date))
            stm.setLong(4, element.idAppointment)
            result = stm.executeUpdate()
        }
        return if (result == 1) Ok(element) else Err(AppointmentError.AppointmentNotUpdated)
    }

    override fun saveAll(elements: Iterable<Appointment>) {
        logger.debug { "AppointmentRepositoryDataBase ->\tsaveAll" }
        elements.forEach { save(it) }
    }

    override fun deleteById(id: Long): Result<Boolean, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tdeleteById" }
        var result: Int
        val sql = """DELETE FROM tAppointment WHERE nIdAppointment = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            result = stm.executeUpdate()
        }
        return if (result == 1) Ok(true) else Err(AppointmentError.AppointmentNotDeleted)
    }

    override fun delete(element: Appointment): Result<Boolean, AppointmentError> {
        logger.debug { "AppointmentRepositoryDataBase ->\tdelete" }
        return deleteById(element.idAppointment)
    }

    override fun deleteAll() {
        logger.debug { "AppointmentRepositoryDataBase ->\tdeleteAll" }
        val sql = """DELETE FROM tAppointment"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.executeUpdate()
        }
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "AppointmentRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        return findAll().count()
    }
}