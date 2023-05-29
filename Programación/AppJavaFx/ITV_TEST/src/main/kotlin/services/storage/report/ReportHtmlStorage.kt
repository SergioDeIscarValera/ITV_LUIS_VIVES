package services.storage.report


import repositories.employee.EmployeeRepository
import repositories.owner.OwnerRepository
import repositories.vehicle.VehicleRepository
import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.*
import dto.ReportDto
import errors.ReportError
import mappers.toClass
import mappers.toDto
import models.Report
import mu.KotlinLogging
import java.io.File
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

private val logger = KotlinLogging.logger {}

class ReportHtmlStorage(
    private val ownerRepository: OwnerRepository,
    private val employeeRepository: EmployeeRepository,
    private val vehicleRepository: VehicleRepository
): ReportStorageService {

    private val fileName = File.separator + "reports.html"

    override fun save(element: Report, filePath: String): Result<Report, ReportError> {
        logger.debug { "ReportHtmlStorage ->\tsave" }
        val file = File(filePath + fileName)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                try {
                    val doc = Document.createShell("")
                    val table = doc.createElement("table")
                    table.attr("border", "1")
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

    override fun saveAll(elements: List<Report>, filePath: String): Result<List<Report>, ReportError> {
        logger.debug { "ReportHtmlStorage ->\tsaveAll" }
        val file = File(filePath + fileName)
        return file.validate(FileAction.WRITE).mapBoth(
            success = {
                try {
                    val doc = Document.createShell("")
                    val title = doc.createElement("h1")
                    title.append("Reports Inspeccionamos Tu Coche:")

                    val table = doc.createElement("table")
                    table.append("<tr><th>ID</th><th>Favorable</th><th>Braking</th><th>Pollution</th><th>Inside</th><th>Lights</th><th>Employee ID</th><th>Employee Name</th><th>Vehicle Car Number</th><th>Brand</th><th>Model</th><th>Owner DNI</th><th>Owner Name</th><th>Owner Phone</th></tr>")
                    elements.forEach { addRowPerReport(it, table) }

                    doc.body().appendChild(title)
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

    override fun loadAll(filePath: String): Result<List<Report>, ReportError> {
        logger.debug { "ReportHtmlStorage ->\tloadAll" }
        val file = File(filePath)
        return file.validate(FileAction.READ).mapBoth(
            success = {
                val doc = Jsoup.parse(file, "UTF-8")
                val reports = mutableListOf<Report>()
                doc.select("tr").forEachIndexed { index, element ->
                    if (index != 0) {
                        val employee = employeeRepository.findById(element.select("td")[6].text().toLong()).get()
                            ?: throw Exception("Employee not found")
                        val vehicle = vehicleRepository.findById(element.select("td")[8].text()).get()
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