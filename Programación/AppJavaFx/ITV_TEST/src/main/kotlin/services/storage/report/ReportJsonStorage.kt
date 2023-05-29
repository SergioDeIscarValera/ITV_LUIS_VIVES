package services.storage.report

import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dto.ReportDto
import errors.ReportError
import mappers.toClass
import mappers.toDto
import models.Report
import mu.KotlinLogging
import java.io.File
import java.lang.Exception

private val logger = KotlinLogging.logger {}

class ReportJsonStorage: ReportStorageService {
    private val fileName = File.separator + "reports.json"

    override fun save(element: Report, filePath: String): Result<Report, ReportError> {
        logger.debug { "ReportJsonStorage ->\tsave" }
        val file = File(filePath + fileName)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jsonString = gson.toJson(element.toDto())
                    file.writeText(jsonString)
                    Ok(element)
                }catch (e: Exception){
                    Err(ReportError.ExportError("JSON"))
                }
            },
            failure = {
                Err(ReportError.ExportError("JSON"))
            }
        )
    }

    override fun saveAll(elements: List<Report>, filePath: String): Result<List<Report>, ReportError> {
        logger.debug { "ReportJsonStorage ->\tsaveAll" }
        val file = File(filePath + fileName)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jsonString = gson.toJson(elements.map { it.toDto() })
                    file.writeText(jsonString)
                    Ok(elements)
                }catch (e: Exception){
                    Err(ReportError.ExportError("JSON"))
                }
            },
            failure = {
                Err(ReportError.ExportError("JSON"))
            }
        )
    }

    override fun loadAll(filePath: String): Result<List<Report>, ReportError> {
        logger.debug { "ReportJsonStorage ->\tloadAll" }
        val file = File(filePath)
        return file.validate(FileAction.READ).mapBoth(
            success = {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val importType = object : TypeToken<List<ReportDto>>() {}.type
                return try {
                    val jsonString = file.readText()
                    val data = gson.fromJson<List<ReportDto>>(jsonString, importType)
                    Ok(data.map { it.toClass() })
                } catch (e: Exception) {
                    Err(ReportError.ImportError("JSON"))
                }
            },
            failure = {
                Err(ReportError.ImportError("JSON"))
            }
        )
    }
}