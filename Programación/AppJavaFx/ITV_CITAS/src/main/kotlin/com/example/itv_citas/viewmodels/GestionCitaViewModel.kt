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
import com.example.itv_citas.states.TypeAction
import com.example.itv_citas.validators.validar
import com.github.michaelbull.result.get
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
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
            setStage(oldValue, newValue)
        }
    }

    private fun setStage(oldValue: CitaState, newValue: CitaState) {
        val horas: List<String> = listOf(
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
            "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
            "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"
        )
        if (citaViewModel.state.value.typeAction == TypeAction.EDIT) {
            val trabajador = employeeRepository.findById(newValue.idTrabajador.toLong()).get()
            state.value = state.value.copy(
                matricula = newValue.matriculaVehicle,
                fecha = newValue.fechaAppointment,
                especialidad = specialtyRepository.findAll().map { it.nombre },
                trabajador = employeeRepository.findAll().map { "${it.id} - ${it.name}" },
                hora = horas,
                especialidadSelected = trabajador?.specialty?.nombre,
                trabajadorSelected = "${trabajador?.id} - ${trabajador?.name}",
                horaSelected = newValue.fechaAppointment.substring(11, 16),
                typeAction = TypeAction.EDIT
            )
        } else {
            state.value = state.value.copy(
                matricula = "",
                fecha = "",
                especialidad = specialtyRepository.findAll().map { it.nombre },
                especialidadSelected = "",
                trabajador = employeeRepository.findAll().map { "${it.id} - ${it.name}" },
                trabajadorSelected = "",
                hora = horas,
                horaSelected = "",
                typeAction = TypeAction.BASE
            )
            state.value = state.value.copy(
                typeAction = TypeAction.NEW
            )
        }
    }

    fun asignarEspecialidad(newValue: String?) {
        if (newValue != null) {
            state.value = state.value.copy(
                trabajadorSelected = newValue
            )
        }
    }

    fun asignarTrabajador(newValue: String?) {
        if (newValue != null) {
            state.value = state.value.copy(
                especialidadSelected = newValue,
                trabajador = employeeRepository.findAll().filter { it.specialty.nombre == newValue }
                    .map { "${it.id} - ${it.name}" }
            )
        }
    }

    fun guardarCita(): Boolean {
        if (state.value.typeAction == TypeAction.NEW) {
            logger.debug { "GestionCitaViewModel -> Guardar cita nueva" }
            val cita = AppointmentDto(
                idEmployee = state.value.trabajadorSelected?.substringBefore(" - ")?.toLongOrNull() ?: 0L,
                carNumber = state.value.matricula,
                date = "${state.value.fecha}T${state.value.horaSelected}:00"
            )
            cita.validar().onSuccess { cita ->
                val newCita = cita.toClass()
                if (vehicleRepository.findAll().find { it.carNumber == newCita.carNumber } == null) {
                    showErrorAlert("Matrícula no válida", "La matrícula no existe en la base de datos")

                } else if (appointmentRepository.findAll()
                        .find { it.carNumber == newCita.carNumber && it.date.isAfter(LocalDateTime.now()) } != null) {
                    showErrorAlert("Vehiculo no válido", "Este vehículo ya tiene una cita pendiente")

                } else if (employeeRepository.findAll().find { it.id == newCita.idEmployee } == null) {
                    showErrorAlert("Trabajador no válido", "El id del trabajador no existe en la base de datos")

                } else if (appointmentRepository.findAll()
                        .filter { it.idEmployee == newCita.idEmployee }
                        .count { it.date in newCita.date..newCita.date.plusMinutes(30) } >= 4) {
                    showErrorAlert(
                        "Trabajador no válido",
                        "Este trabajador tiene ya 4 citas pendientes en el mismo rango de media hora"
                    )

                } else if (newCita.date.isBefore(LocalDateTime.now())) {
                    showErrorAlert("Fecha no válida", "La fecha debe ser posterior a la fecha actual")

                } else if (appointmentRepository.findAll()
                        .count { it.date in newCita.date..newCita.date.plusMinutes(30) } >= 8) {
                    showErrorAlert("Hora no válida", "Ya hay 8 citas en esta media hora")

                } else {
                    appointmentRepository.save(newCita)
                    citaViewModel.state.value =
                        citaViewModel.state.value.copy(citas = appointmentRepository.findAll().toList())
                    return true
                }
            }

        } else {
            logger.debug { "GestionCitaViewModel -> Guardar cita editada" }
            val cita = AppointmentDto(
                idEmployee = state.value.trabajadorSelected?.substringBefore(" - ")?.toLongOrNull() ?: 0L,
                carNumber = state.value.matricula,
                date = "${state.value.fecha}T${state.value.horaSelected}:00"
            )
            cita.validar().onSuccess { cita ->
                val newCita = cita.toClass()
                if (vehicleRepository.findAll().find { it.carNumber == newCita.carNumber } == null) {
                    showErrorAlert("Matrícula no válida", "La matrícula no existe en la base de datos")

                } else if (employeeRepository.findAll().find { it.id == newCita.idEmployee } == null) {
                    showErrorAlert("Trabajador no válido", "El id del trabajador no existe en la base de datos")

                } else if (appointmentRepository.findAll()
                        .filter { it.idEmployee == newCita.idEmployee }
                        .count { it.date in newCita.date..newCita.date.plusMinutes(30) } >= 4) {
                    showErrorAlert(
                        "Trabajador no válido",
                        "Este trabajador tiene ya 4 citas pendientes en el mismo rango de media hora"
                    )

//                } else if (newCita.date.isBefore(LocalDateTime.now())) {
//                    showErrorAlert("Fecha no válida", "La fecha debe ser posterior a la fecha actual")

                } else if (appointmentRepository.findAll()
                        .count { it.date in newCita.date..newCita.date.plusMinutes(30) } >= 8) {
                    showErrorAlert("Hora no válida", "Ya hay 8 citas en esta media hora")

                } else {
                    appointmentRepository.save(newCita)
                    citaViewModel.state.value =
                        citaViewModel.state.value.copy(citas = appointmentRepository.findAll().toList())
                    return true
                }
            }
        }
        return false
    }

}