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

private val logger = KotlinLogging.logger {}

class EmployeeRepositoryDataBase: EmployeeRepository, KoinComponent{
    private val dataBaseManager by inject<DataBaseManager>()
    private val specialtyRepository by inject<SpecialtyRepository>(named("SpecialtyBBDD"))

    override fun findAll(): Iterable<Employee> {
        logger.debug { "EmployeeRepositoryDataBase ->\tfindAll" }
        val employees = mutableListOf<Employee>()
        val sql = """SELECT * FROM tEmployees"""
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
        val sql = """SELECT * FROM tEmployees WHERE nIdEmployee = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                employee = resultToEmployee(result)
            }
        }
        return if (employee != null) Ok(employee!!) else Err(EmployeeError.EmployeeNotFound)
    }

    private fun resultToEmployee(result: ResultSet) = Employee(
        result.getLong("nIdEmployee"),
        result.getString("cName"),
        result.getString("cEmail"),
        result.getString("cUserName"),
        result.getString("cPhone"),
        result.getString("cPassword"),
        specialtyRepository.findById(result.getString("cIdSpecialty"))
            .onFailure { throw Exception("Specialty not found") }.get()!!,
        result.getLong("nIdStation"),
        result.getLong("nIdResponsible")
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