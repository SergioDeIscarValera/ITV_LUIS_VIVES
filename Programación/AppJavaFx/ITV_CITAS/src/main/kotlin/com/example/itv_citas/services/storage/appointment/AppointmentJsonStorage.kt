package com.example.itv_citas.services.storage.appointment

import com.example.itv_citas.config.AppConfig
import com.example.itv_citas.dto.AppointmentDto
import com.example.itv_citas.errors.AppointmentFileError
import com.example.itv_citas.mappers.toClass
import com.example.itv_citas.mappers.toDto
import com.example.itv_citas.models.Appointment
import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.lang.Exception

private val logger = KotlinLogging.logger {}

class AppointmentJsonStorage: AppointmentStorageService, KoinComponent {
    private val appConfig by inject<AppConfig>()

    // Por ahora, luego hay que hacer para pasar la ruta de donde se quiera exportar/importar
    private val localPath = "${appConfig.appData}${File.separator}appointment.json"

    override fun save(element: Appointment): Result<Appointment, AppointmentFileError> {
        logger.debug { "AppointmentJsonStorage ->\tsave" }
        val file = File(localPath)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jsonString = gson.toJson(element.toDto())
                    file.writeText(jsonString)
                    Ok(element)
                }catch (e: Exception){
                    Err(AppointmentFileError.ExportError("JSON"))
                }
            },
            failure = {
                Err(AppointmentFileError.ExportError("JSON"))
            }
        )
    }

    override fun saveAll(elements: List<Appointment>): Result<List<Appointment>, AppointmentFileError> {
        logger.debug { "AppointmentJsonStorage ->\tsaveAll" }
        val file = File(localPath)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                return try {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val jsonString = gson.toJson(elements.map { it.toDto() })
                    file.writeText(jsonString)
                    Ok(elements)
                }catch (e: Exception){
                    Err(AppointmentFileError.ExportError("JSON"))
                }
            },
            failure = {
                Err(AppointmentFileError.ExportError("JSON"))
            }
        )
    }

    override fun loadAll(): Result<List<Appointment>, AppointmentFileError> {
        logger.debug { "AppointmentJsonStorage ->\tloadAll" }
        val file = File(localPath)
        return file.validate(FileAction.READ).mapBoth(
            success = {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val importType = object : TypeToken<List<AppointmentDto>>() {}.type
                return try {
                    val jsonString = file.readText()
                    val data = gson.fromJson<List<AppointmentDto>>(jsonString, importType)
                    Ok(data.map { it.toClass() })
                } catch (e: Exception) {
                    Err(AppointmentFileError.ImportError("JSON"))
                }
            },
            failure = {
                Err(AppointmentFileError.ImportError("JSON"))
            }
        )
    }
}