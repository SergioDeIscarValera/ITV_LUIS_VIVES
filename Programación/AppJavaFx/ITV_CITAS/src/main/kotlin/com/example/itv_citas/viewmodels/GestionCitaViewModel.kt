package com.example.itv_citas.viewmodels

import com.example.itv_citas.dto.AppointmentDto
import com.example.itv_citas.mappers.toClass
import com.example.itv_citas.repositories.appointment.AppointmentRepository
import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.repositories.specialty.SpecialtyRepository
import com.example.itv_citas.repositories.vehicle.VehicleRepository
import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.example.itv_citas.states.CitaState
import com.example.itv_citas.states.GestionCitaState
import com.example.itv_citas.states.TypeActionView
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.get
import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.onFailure
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.time.LocalDate
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

class GestionCitaViewModel: KoinComponent {

    private val citaViewModel by inject<CitaViewModel>()
    private val employeeRepository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    private val specialtyRepository by inject<SpecialtyRepository>(named("SpecialtyBBDD"))
    private val appointmentRepository by inject<AppointmentRepository>(named("AppointmentBBDD"))
    private val vehicleRepository by inject<VehicleRepository>(named("VehicleBBDD"))

    val state: ObjectProperty<GestionCitaState> = SimpleObjectProperty(GestionCitaState())

    init {
        citaViewModel.state.addListener { _, oldValue, newValue ->
            if (newValue.typeAction == oldValue.typeAction) return@addListener
            setStage(newValue)
        }
    }

    private fun setStage(newValue: CitaState) {
        val horas: List<String> = listOf(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"
        )
        val specialties = listOf("") + specialtyRepository.findAll().map { it.nombre }
        val employees = employeeRepository.findAll().map { "${it.id} - ${it.name}" }
        state.value = state.value.copy(
            matricula = "",
            fecha = LocalDate.now().toString(),
            especialidades = specialties,
            trabajadores = employees,
            hora = horas,
            trabajadorSelected = employees.first(),
            horaSelected = horas.first(),
            typeAction = newValue.typeAction
        )
        if (newValue.typeAction == TypeActionView.EDIT) {
            val trabajador = employeeRepository.findById(newValue.idTrabajador.toLong()).get() ?: return
            state.value = state.value.copy(
                matricula = newValue.matriculaVehicle,
                fecha = newValue.fechaAppointment.split("T").first(),
                trabajadorSelected = "${trabajador.id} - ${trabajador.name}",
                horaSelected = LocalDateTime.parse(newValue.fechaAppointment).toLocalTime().toString(),
            )
        }
    }

    fun filtrarPorEspecialidad(newValue: String){
        val trabajadores = employeeRepository.findAll().filter {
            when(newValue){
                "" -> true
                else -> it.specialty.nombre == newValue
            }
        }.map { "${it.id} - ${it.name}" }

        if (trabajadores.isEmpty()) {
            showErrorAlert("Error al filtrar", "No hay trabajadores con esa especialidad")
            return
        }

        state.value = state.value.copy(
            trabajadores = trabajadores,
            trabajadorSelected = trabajadores.first()
        )
    }

    fun guardarCita(): Boolean{
        val citaDto = AppointmentDto(
            idEmployee = state.value.trabajadorSelected.substringBefore(" - ").toLong(),
            carNumber = state.value.matricula,
            date = "${state.value.fecha}T${state.value.horaSelected}:00"
        )
        citaDto.validate()
            .andThen {
                vehicleRepository.findById(it.carNumber)
                    .onFailure { err ->
                        showErrorAlert("Error al guardar", err.message)
                        return false
                    }

                // Filtrar las citas del tramo de 30 minutos
                it.toClass().validate(
                    appointmentRepository.findAll().toList().filter { cita ->
                        cita.date.toLocalDate() == it.toClass().date.toLocalDate() &&
                                cita.date.toLocalTime().hour == it.toClass().date.toLocalTime().hour &&
                                cita.date.toLocalTime().minute in it.toClass().date.toLocalTime().minute..(it.toClass().date.toLocalTime().minute + 30)
                    }
                )
            }
            .andThen { appointmentRepository.save(it) }
            .mapBoth(
                success = { return true },
                failure = {
                    showErrorAlert("Error al guardar", it.message)
                    return false
                }
            )
    }

    fun updateMainView() {
        citaViewModel.updateViewList()
        citaViewModel.clearSelected()
    }
}