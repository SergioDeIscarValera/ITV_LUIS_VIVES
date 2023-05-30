package com.example.itv_citas.repositories.employee

import com.example.itv_citas.errors.EmployeeError
import com.example.itv_citas.models.Employee
import com.example.itv_citas.repositories.specialty.SpecialtyRepository
import com.example.itv_citas.services.database.DataBaseManager
import com.github.michaelbull.result.*
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.sql.ResultSet
import java.sql.Statement

private val logger = KotlinLogging.logger {}

class EmployeeRepositoryDataBase: EmployeeRepository, KoinComponent{
    private val dataBaseManager by inject<DataBaseManager>()
    private val specialtyRepository by inject<SpecialtyRepository>(named("SpecialtyBBDD"))
    override fun saveAll(employees: List<Employee>): Result<List<Employee>, EmployeeError> {
        logger.debug { "EmployeeRepositoryDataBase ->\tsaveAll" }
        employees.forEach {
            if (findById(it.id).get() != null){
                update(it).onFailure { err ->
                    return Err(err)
                }
            }else {
                save(it).onFailure { err ->
                    return Err(err)
                }
            }
        }
        return Ok(employees)
    }

    private fun save(employee: Employee): Result<Employee, EmployeeError>{
        logger.debug { "EmployeeRepositoryDataBase ->\tsave" }
        var newId = 0L
        val sql = """INSERT INTO tTrabajadores (cNombre, cCorreoElectronico, cNombreUsuario, cTelefono, cPassword, cNombreEspecialidad, nId_Estacion) VALUES (?, ?, ?, ?, ?, ?, ?)"""
        dataBaseManager.dataBase.use {
            it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stm ->
                stm.setString(1, employee.name)
                stm.setString(2, employee.email)
                stm.setString(3, employee.userName)
                stm.setString(4, employee.phone)
                stm.setString(5, employee.password)
                stm.setString(6, employee.specialty.nombre)
                stm.setLong(7, employee.idEstacion)
                stm.executeUpdate()
                val result = stm.generatedKeys
                if (result.next()) {
                    newId = result.getLong(1)
                }
            }
        }
        return if (newId != 0L) Ok(employee.copy(id = newId)) else Err(EmployeeError.EmployeeNotSaved)
    }

    private fun update(employee: Employee): Result<Employee, EmployeeError>{
        logger.debug { "EmployeeRepositoryDataBase ->\tupdate" }
        var result: Int
        val sql = """UPDATE tTrabajadores SET cNombre = ?, cCorreoElectronico = ?, cNombreUsuario = ?, cTelefono = ?, cPassword = ?, cNombreEspecialidad = ?, nId_Estacion = ? WHERE nId_Trabajador = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, employee.name)
            stm.setString(2, employee.email)
            stm.setString(3, employee.userName)
            stm.setString(4, employee.phone)
            stm.setString(5, employee.password)
            stm.setString(6, employee.specialty.nombre)
            stm.setLong(7, employee.idEstacion)
            stm.setLong(8, employee.id)
            result = stm.executeUpdate()
        }
        return if (result == 1) Ok(employee) else Err(EmployeeError.EmployeeNotUpdate)
    }

    override fun findAll(): Iterable<Employee> {
        logger.debug { "EmployeeRepositoryDataBase ->\tfindAll" }
        val employees = mutableListOf<Employee>()
        val sql = """SELECT * FROM tTrabajadores"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                employees.add(
                    resultToEmployee(result)
                )
            }
        }
        return employees.toList()
    }

    override fun findById(id: Long): Result<Employee, EmployeeError> {
        logger.debug { "EmployeeRepositoryDataBase ->\tfindById" }
        var employee:Employee? = null
        val sql = """SELECT * FROM tTrabajadores WHERE nId_Trabajador = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                employee = resultToEmployee(result)
            }
        }
        return if (employee != null) Ok(employee!!) else Err(EmployeeError.EmployeeNotFound)
    }
    override fun findByUser(user: String): Result<Employee, EmployeeError> {
        logger.debug { "EmployeeRepositoryDataBAse ->\tfindByUser" }

        var employee:Employee?  = null
        val sql = """ SELECT * FROM tTrabajadores WHERE cNombreUsuario = ?"""

        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1,user)
            val result = stm.executeQuery()

            if (result.next()){
                employee= resultToEmployee(result)
            }
        }
        return if (employee!=null) Ok(employee!!) else Err(EmployeeError.UserNotFound)
    }

    override fun findByEmail(email: String): Result<Employee, EmployeeError> {
        logger.debug { "EmployeeRepositoryDataBAse ->\tfindByEmail" }

        var employee:Employee?  = null
        val sql = """ SELECT * FROM tTrabajadores WHERE cCorreoElectronico = ?"""

        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1,email)
            val result = stm.executeQuery()

            if (result.next()){
                employee= resultToEmployee(result)
            }
        }
        return if (employee!=null) Ok(employee!!) else Err(EmployeeError.UserNotFound)
    }

    private fun resultToEmployee(result: ResultSet) = Employee(
        result.getLong("nId_Trabajador"),
        result.getString("cNombre"),
        result.getString("cCorreoElectronico"),
        result.getString("cNombreUsuario"),
        result.getString("cTelefono"),
        result.getString("cPassword"),
        specialtyRepository.findById(result.getString("cNombreEspecialidad"))
            .onFailure { throw Exception("Specialty not found") }.get()!!,
        result.getLong("nId_Estacion"),
        result.getLong("nId_Responsable")
    )

    override fun existsById(id: Long): Boolean {
        logger.debug { "EmployeeRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        logger.debug { "EmployeeRepositoryDataBase ->\tcount" }
        return findAll().count()
    }
}