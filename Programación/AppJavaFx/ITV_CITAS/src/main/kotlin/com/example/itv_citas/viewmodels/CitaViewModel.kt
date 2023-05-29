package com.example.itv_citas.viewmodels

import com.example.itv_citas.models.Appointment
import com.example.itv_citas.models.enums.TypeVehicle
import com.example.itv_citas.repositories.appointment.AppointmentRepository
import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.repositories.owner.OwnerRepository
import com.example.itv_citas.repositories.vehicle.VehicleRepository
import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.example.itv_citas.route.RoutesManager.showInfoAlert
import com.example.itv_citas.services.storage.appointment.AppointmentStorageService
import com.example.itv_citas.services.storage.employee.EmployeeStorageService
import com.example.itv_citas.states.CitaState
import com.github.michaelbull.result.*
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.time.LocalDate

private val logger = KotlinLogging.logger {  }

class CitaViewModel: KoinComponent {
    private val citasRepository by inject<AppointmentRepository>(named("AppointmentBBDD"))
    private val trabajadorRepository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    private val vehicleRepository by inject<VehicleRepository>(named("VehicleBBDD"))
    private val ownerRepository by inject<OwnerRepository>(named("OwnerBBDD"))

    private val citasJsonStorage by inject<AppointmentStorageService>(named("AppointmentJSON"))
    private val trabajadorCsvStorage by inject<EmployeeStorageService>(named("EmployeeCSV"))

    val state: ObjectProperty<CitaState> = SimpleObjectProperty(CitaState())

    init {
        updateViewList()
        loadTipos()
    }

    private fun loadTipos() {
        state.value = state.value.copy(
            tipos = listOf(
                "",
                TypeVehicle.TURISMO.value,
                TypeVehicle.FURGONETA.value,
                TypeVehicle.CAMION.value,
                TypeVehicle.MOTOCICLETA.value
            )
        )
    }

    fun updateViewList() {
        state.value = state.value.copy(
            citas = citasRepository.findAll().toList()
        )
    }

    fun clearSelected(){
        state.value = state.value.copy(
            marcaVehicle = "",
            tipoVehicle = "",
            fechaMatriculacionVehicle = "",
            modeloVehicle = "",
            motorVehicle = "",
            fehcaUltimaRevisionVehicle = "",
            dniOwner = "",
            nombreOwner = "",
            apellidoOwner = "",
            telefonoOwner = "",
            correoOwner = ""
        )
    }

    fun exportCitasToJson(path: String) {
        logger.debug { "CitaViewModel ->\texportCitasToJson" }
        citasJsonStorage.saveAll(citasRepository.findAll().toList(), path).mapBoth(
            success = {
                showInfoAlert("Citas exportadas correctamente (${it.size})")
            },
            failure = {
                showErrorAlert("Error al exportar las citas", it.message)
            }
        )
    }

    fun exportTrabajadoresCsv(path: String) {
        logger.debug { "CitaViewModel ->\texportTrabajadoresCsv" }
        trabajadorCsvStorage.saveAll(trabajadorRepository.findAll().toList(), path).mapBoth(
            success = {
                showInfoAlert("Trabajadores exportados correctamente (${it.size})")
            },
            failure = {
                showErrorAlert("Error al exportar los trabajadores", it.message)
            }
        )
    }

    fun importCitasFromJson(path: String) {
        logger.debug { "CitaViewModel ->\timportCitasFromJson" }
        citasJsonStorage.loadAll(path).mapBoth(
            success = {
                citasRepository.saveAll(it)
                showInfoAlert("Citas importadas correctamente (${it.size})")
            },
            failure = {
                showErrorAlert("Error al importar las citas", it.message)
            }
        )
        updateViewList()
    }

    fun importTrabajadoresFromCsv(path: String) {
        logger.debug { "CitaViewModel ->\timportTrabajadoresFromCsv" }
        trabajadorCsvStorage.loadAll(path).mapBoth(
            success = {
                trabajadorRepository.saveAll(it)
                showInfoAlert("Trabajadores importados correctamente (${it.size})")
            },
            failure = {
                showErrorAlert("Error al importar los trabajadores", it.message)
            }
        )
    }

    fun citasFilteredList(tipo: String, textFilter: String, dateFilter: Pair<LocalDate?, LocalDate?>) {
        logger.debug { "CitaViewModel ->\tcitasFilteredList" }
        var appointments = citasRepository.findAll().toList()
        if (dateFilter.first != null && dateFilter.second != null)
            appointments = appointments.filter { it.date.toLocalDate() in dateFilter.first!!..dateFilter.second!! }
        val vehicleCarNumberOk = appointments
            .asSequence()
            .map {
                vehicleRepository.findById(it.carNumber).get()!!
            }.filter {
                when(tipo){
                    TypeVehicle.TURISMO.value -> it.type == TypeVehicle.TURISMO
                    TypeVehicle.FURGONETA.value -> it.type == TypeVehicle.FURGONETA
                    TypeVehicle.CAMION.value -> it.type == TypeVehicle.CAMION
                    TypeVehicle.MOTOCICLETA.value -> it.type == TypeVehicle.MOTOCICLETA
                    else -> true
                }
            }.map { it.carNumber }.filter { it.startsWith(textFilter, true) }.toList()

        state.value = state.value.copy(
            citas = appointments.toList().filter {
                vehicleCarNumberOk.contains(it.carNumber)
            }
        )
    }

    fun updateCitaSelected(it: Appointment) {
        logger.debug { "CitaViewModel ->\tupdateCitaSelected" }
        val vehicle = vehicleRepository.findById(it.carNumber).onFailure {
            showErrorAlert("Error al obtener el vehiculo de la cita", it.message)
            return
        }.get()!!

        val owner = ownerRepository.findById(vehicle.dniOwner).onFailure {
            showErrorAlert("Error al obtener el propietario del vehiculo", it.message)
            return
        }.get()!!

        state.value = state.value.copy(
            fechaAppointment = it.date.toString(),
            idTrabajador = it.idEmployee.toString(),

            matriculaVehicle = vehicle.carNumber,
            marcaVehicle = vehicle.brand,
            tipoVehicle = vehicle.type.value,
            fechaMatriculacionVehicle = vehicle.matriculationDate.toString(),
            modeloVehicle = vehicle.model,
            motorVehicle = vehicle.motor.value,
            fehcaUltimaRevisionVehicle = vehicle.lastItvDate.toString(),

            dniOwner = owner.ownerDNI,
            nombreOwner = owner.name,
            apellidoOwner = owner.lastName,
            telefonoOwner = owner.phone,
            correoOwner = owner.email,
        )
    }

    fun exportCitaToJson(path: String) {
        logger.debug { "CitaViewModel ->\texportCitaToJson" }
        citasRepository.findById(state.value.matriculaVehicle)
            .onFailure { showErrorAlert("Error al obtener la cita", it.message) }
            .onSuccess {
                citasJsonStorage.save(it, path).mapBoth(
                    success = {
                        showInfoAlert("Cita exportada correctamente")
                    },
                    failure = {
                        showErrorAlert("Error al exportar la cita", it.message)
                    }
                )
            }
    }

    fun deleteCitaSelected() {
        citasRepository.deleteById(state.value.matriculaVehicle).mapBoth(
            success = {
                showInfoAlert("Cita eliminada correctamente")
                updateViewList()
                clearSelected()
            },
            failure = {
                showErrorAlert("Error al eliminar la cita", it.message)
            }
        )
    }
}