package com.example.itv_citas.services.storage.report

import com.example.itv_citas.config.AppConfig
import com.example.itv_citas.dto.ReportDto
import com.example.itv_citas.errors.ReportError
import com.example.itv_citas.mappers.toClass
import com.example.itv_citas.mappers.toDto
import com.example.itv_citas.models.Report
import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.repositories.owner.OwnerRepository
import com.example.itv_citas.repositories.vehicle.VehicleRepository
import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.*
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.koin.core.qualifier.named

private val logger = KotlinLogging.logger {}

class ReportHtmlStorage: ReportStorageService, KoinComponent {
    private val appConfig by inject<AppConfig>()

    private val ownerRepository by inject<OwnerRepository>(named("OwnerBBDD"))
    private val employeeRepository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    private val vehicleRepository by inject<VehicleRepository>(named("VehicleBBDD"))

    // Por ahora, luego hay que hacer para pasar la ruta de donde se quiera exportar/importar
    private val localPath = "${appConfig.appData}${File.separator}report.html"

    override fun save(element: Report): Result<Report, ReportError> {
        logger.debug { "ReportHtmlStorage ->\tsave" }
        val file = File(localPath)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                try {
                    val doc = Document.createShell("")
                    val table = doc.createElement("table")
                    table.append("<tr><th>ID</th><th>Favorable</th><th>Braking</th><th>Pollution</th><th>Inside</th><th>Lights</th><th>Employee ID</th><th>Employee Name</th><th>Vehicle Car Number</th><th>Brand</th><th>Model</th><th>Owner DNI</th><th>Owner Name</th><th>Owner Phone</th></tr>")
                    addRowPerReport(element, table)
                    doc.body().appendChild(table)
                    file.writeText(doc.outerHtml())
                    Ok(element)
                }catch (e: Exception) {
                    Err(ReportError.ExportError("HTML"))
                }
            },
            failure = {
                Err(ReportError.ExportError("HTML"))
            }
        )
    }

    private fun addRowPerReport(element: Report, table: Element) {
        val owner = ownerRepository.findById(element.vehicle.dniOwner).get() ?: throw Exception("Owner not found")
        table.append("<tr><td>${element.id}</td><td>${element.favorable}</td><td>${element.braking}</td><td>${element.pollution}</td><td>${element.inside}</td><td>${element.lights}</td><td>${element.employee.id}</td><td>${element.employee.name}</td><td>${element.vehicle.carNumber}</td><td>${element.vehicle.brand}</td><td>${element.vehicle.model}</td><td>${owner.ownerDNI}</td><td>${owner.name}</td><td>${owner.phone}</td></tr>")
    }

    override fun saveAll(elements: List<Report>): Result<List<Report>, ReportError> {
        logger.debug { "ReportHtmlStorage ->\tsaveAll" }
        val file = File(localPath)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                try {
                    val doc = Document.createShell("")
                    val table = doc.createElement("table")
                    table.append("<tr><th>ID</th><th>Favorable</th><th>Braking</th><th>Pollution</th><th>Inside</th><th>Lights</th><th>Employee ID</th><th>Employee Name</th><th>Vehicle Car Number</th><th>Brand</th><th>Model</th><th>Owner DNI</th><th>Owner Name</th><th>Owner Phone</th></tr>")
                    elements.forEach { addRowPerReport(it, table) }
                    doc.body().appendChild(table)
                    file.writeText(doc.outerHtml())
                    Ok(elements)
                }catch (e: Exception) {
                    Err(ReportError.ExportError("HTML"))
                }
            },
            failure = {
                Err(ReportError.ExportError("HTML"))
            }
        )
    }

    override fun loadAll(): Result<List<Report>, ReportError> {
        logger.debug { "ReportHtmlStorage ->\tloadAll" }
        val file = File(localPath)
        return file.validate(FileAction.READ).mapBoth(
            success = {
                val doc = Jsoup.parse(file, "UTF-8")
                val reports = mutableListOf<Report>()
                doc.select("tr").forEachIndexed { index, element ->
                    if (index != 0) {
                        val employee = employeeRepository.findById(element.select("td")[6].text().toLong()).get()
                            ?: throw Exception("Employee not found")
                        val vehicle = vehicleRepository.findById(element.select("td")[7].text()).get()
                            ?: throw Exception("Vehicle not found")
                        val owner = ownerRepository.findById(element.select("td")[11].text()).get()
                            ?: throw Exception("Owner not found")
                        reports.add(
                            ReportDto(
                                id = element.select("td")[0].text(),
                                favorable = element.select("td")[1].text(),
                                braking = element.select("td")[2].text(),
                                pollution = element.select("td")[3].text(),
                                inside = element.select("td")[4].text(),
                                lights = element.select("td")[5].text(),
                                employee = employee.toDto(),
                                vehicle = vehicle.toDto(),
                                owner = owner.toDto()
                            ).toClass()
                        )
                    }
                }
                Ok(reports)
            },
            failure = {
                Err(ReportError.ImportError("HTML"))
            }
        )
    }
}