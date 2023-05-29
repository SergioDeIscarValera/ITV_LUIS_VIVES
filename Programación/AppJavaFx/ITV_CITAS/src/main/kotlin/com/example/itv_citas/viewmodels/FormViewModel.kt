package com.example.itv_citas.viewmodels


import com.example.itv_citas.dto.ReportDto
import com.example.itv_citas.mappers.toClass
import com.example.itv_citas.mappers.toDto
import com.example.itv_citas.models.Report
import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.repositories.owner.OwnerRepository
import com.example.itv_citas.repositories.vehicle.VehicleRepository
import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.example.itv_citas.route.TypeChoose
import com.example.itv_citas.services.storage.report.ReportStorageService
import com.example.itv_citas.states.FormularioState
import com.example.itv_citas.validators.validar
import com.github.michaelbull.result.get
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

private val logger = KotlinLogging.logger {}

class FormViewModel: KoinComponent {
    private val citaViewModel by inject<CitaViewModel>()
    private val employeeRepository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    private val ownerRepository by inject<OwnerRepository>(named("OwnerBBDD"))
    private val vehicleRepository by inject<VehicleRepository>(named("VehicleBBDD"))
    private val reportJsonStorage by inject<ReportStorageService>(named("ReportJSON"))
    private val reportHtmlStorage by inject<ReportStorageService>(named("ReportHTML"))

    val state: ObjectProperty<FormularioState> = SimpleObjectProperty(FormularioState())

    init {
        setState()
    }

    private fun setState() {
        citaViewModel.state.addListener { _, _, newValue ->
            state.value = state.value.copy(
                fecha = newValue.fechaAppointment,
                vehiculo = newValue.matriculaVehicle,
                propietario = newValue.dniOwner,
                trabajador = newValue.idTrabajador
            )
        }
    }

    fun guardar(): Boolean {
        logger.debug { "FormViewModel -> Guardar informe" }
        val report = generarReport()
        report.validar().onFailure {
            showErrorAlert("Los datos no son correctos", it.message)
            return false
        }.onSuccess {
            return true
        }
        return false
    }

    private fun generarReport(): ReportDto {
        return ReportDto(
            id = "0",
            favorable = state.value.apto.toString(),
            braking = state.value.frenado,
            pollution = state.value.contaminacion,
            inside = state.value.interior.toString(),
            lights = state.value.luces.toString(),
            employee = (employeeRepository.findById(state.value.trabajador.toLong()).get()!!).toDto(),
            vehicle = (vehicleRepository.findById(state.value.vehiculo).get()!!).toDto(),
            owner = (ownerRepository.findById(state.value.propietario).get()!!).toDto()
        )
    }

    fun exportInformeToJson(path: String) {
        logger.debug { "FormViewModel -> Exportar informe a JSON" }

        val informe = generarReport().toClass()

        reportJsonStorage.save(informe, path).mapBoth(
            success = {
                RoutesManager.showInfoAlert("Informe exportado correctamente")
            },
            failure = {
                showErrorAlert("Error al exportar el informe", it.message)
            }
        )
    }

    fun exportarInformeToHtml(path: String) {
        logger.debug { "FormViewModel -> Exportar informe a HTML" }

        val informe = generarReport().toClass()

        reportHtmlStorage.save(informe, path).mapBoth(
            success = {
                RoutesManager.showInfoAlert("Informe exportado correctamente")
            },
            failure = {
                showErrorAlert("Error al exportar el informe", it.message)
            }
        )
    }
}