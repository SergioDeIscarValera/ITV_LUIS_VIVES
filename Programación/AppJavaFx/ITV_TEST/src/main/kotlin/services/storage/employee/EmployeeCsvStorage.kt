package services.storage.employee

import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import dto.EmployeeDto
import dto.SpecialtyDto
import errors.EmployeeError
import mappers.toClass
import models.Employee
import mu.KotlinLogging
import java.io.File

private val logger = KotlinLogging.logger {}

object EmployeeCsvStorage: EmployeeStorageService {
    private val fileName = File.separator + "employees.csv"

    private val header =
        "id,name,email,userName,phone,password,specialtyNombre,specialtySalario,idStation,idResponsable\n"

    override fun save(element: Employee, filePath: String): Result<Employee, EmployeeError> {
        logger.debug { "EmployeeCsvStorage ->\tsave" }
        val file = File(filePath + fileName)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    file.writeText(header)
                    file.appendText(
                        element.toCsvRow()
                    )
                    Ok(element)
                } catch (e: Exception) {
                    Err(EmployeeError.ExportError("CSV"))
                }
            },
            failure = {
                Err(EmployeeError.ExportError("CSV"))
            }
        )
    }

    override fun saveAll(elements: List<Employee>, filePath: String): Result<List<Employee>, EmployeeError> {
        logger.debug { "EmployeeCsvStorage ->\tsaveAll" }
        val file = File(filePath + fileName)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    file.writeText(header)
                    elements.forEach {
                        file.appendText(
                            it.toCsvRow()
                        )
                    }
                    Ok(elements)
                } catch (e: Exception) {
                    Err(EmployeeError.ExportError("CSV"))
                }
            },
            failure = {
                Err(EmployeeError.ExportError("CSV"))
            }
        )
    }

    override fun loadAll(filePath: String): Result<List<Employee>, EmployeeError> {
        logger.debug { "EmployeeCsvStorage ->\tloadAll" }
        val file = File(filePath)
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
                                        celda[6],
                                        celda[7]
                                    ),
                                    celda[8],
                                    celda[9]
                                ).toClass()
                            }
                    )
                } catch (e: Exception) {
                    Err(EmployeeError.ImportError("CSV"))
                }
            },
            failure = {
                Err(EmployeeError.ExportError("CSV"))
            }
        )
    }
}

fun Employee.toCsvRow(): String {
    return "${this.id},${this.name},${this.email},${this.userName},${this.phone},${this.password},${this.specialty.nombre},${this.specialty.salario},${this.idEstacion},${this.idResponsable}\n"
}
