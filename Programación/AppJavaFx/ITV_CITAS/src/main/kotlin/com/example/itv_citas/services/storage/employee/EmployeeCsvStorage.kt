package com.example.itv_citas.services.storage.employee

import com.example.itv_citas.config.AppConfig
import com.example.itv_citas.dto.EmployeeDto
import com.example.itv_citas.dto.SpecialtyDto
import com.example.itv_citas.errors.EmployeeError
import com.example.itv_citas.mappers.toClass
import com.example.itv_citas.mappers.toDto
import com.example.itv_citas.models.Employee
import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

private val logger = KotlinLogging.logger {}

class EmployeeCsvStorage: EmployeeStorageService, KoinComponent {
    private val appConfig by inject<AppConfig>()

    // Por ahora, luego hay que hacer para pasar la ruta de donde se quiera exportar/importar
    private val localPath = "${appConfig.appData}${File.separator}employee.csv"

    override fun save(element: Employee): Result<Employee, EmployeeError> {
        logger.debug { "EmployeeCsvStorage ->\tsave" }
        val file = File(localPath)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    file.writeText("id,name,email,userName,phone,password,specialtyNombre,specialtySalario,idStation,idResponsable\n")
                    file.appendText(
                        employeeToCsv(element.toDto())
                    )
                    Ok(element)
                }catch (e: Exception){
                    Err(EmployeeError.ExportError("CSV"))
                }
            },
            failure = {
                Err(EmployeeError.ExportError("CSV"))
            }
        )
    }

    override fun saveAll(elements: List<Employee>): Result<List<Employee>, EmployeeError> {
        logger.debug { "EmployeeCsvStorage ->\tsaveAll" }
        val file = File(localPath)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    file.writeText("id,name,email,userName,phone,password,specialtyNombre,specialtySalario,idStation,idResponsable\n")
                    file.appendText(
                        elements.joinToString { employeeToCsv(it.toDto()) }
                    )
                    Ok(elements)
                }catch (e: Exception){
                    Err(EmployeeError.ExportError("CSV"))
                }
            },
            failure = {
                Err(EmployeeError.ExportError("CSV"))
            }
        )
    }

    private fun employeeToCsv(employee: EmployeeDto): String {
        return "${employee.id},${employee.name},${employee.email},${employee.userName},${employee.phone},${employee.password},${employee.specialty.nombre};${employee.specialty.salario},${employee.idStation},${employee.idResponsable}\n"
    }

    override fun loadAll(): Result<List<Employee>, EmployeeError> {
        logger.debug { "EmployeeCsvStorage ->\tloadAll" }
        val file = File(localPath)
        return file.validate(FileAction.READ).mapBoth(
            success = {
                try {
                    Ok(
                        file.readLines()
                            .drop(1)
                            .map { it.split(",") }
                            .map { celda ->
                                EmployeeDto(
                                    celda[0],
                                    celda[1],
                                    celda[2],
                                    celda[3],
                                    celda[4],
                                    celda[5],
                                    SpecialtyDto(
                                        celda[6].split(";")[0],
                                        celda[6].split(";")[1]
                                    ),
                                    celda[7],
                                    celda[8]
                                ).toClass()
                            }
                    )
                }catch (e: Exception) {
                    Err(EmployeeError.ImportError("CSV"))
                }
            },
            failure = {
                Err(EmployeeError.ExportError("CSV"))
            }
        )
    }
}