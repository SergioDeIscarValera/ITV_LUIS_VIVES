package com.example.itv_citas.controllers

import com.example.itv_citas.route.RoutesManager.showInfoAlert
import com.example.itv_citas.states.GestionCitaState
import com.example.itv_citas.states.TypeAction
import com.example.itv_citas.viewmodels.GestionCitaViewModel
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate

private val logger = KotlinLogging.logger{}

class GestionCitaController: KoinComponent {

    private val gestionCitaViewModel by inject<GestionCitaViewModel>()

    @FXML
    private lateinit var textMatricula: TextField

    @FXML
    private lateinit var textEspecialidad: ChoiceBox<String>

    @FXML
    private lateinit var textTrabajador: ChoiceBox<String>

    @FXML
    private lateinit var textFecha: DatePicker

    @FXML
    private lateinit var textHora: ChoiceBox<String>

    @FXML
    private lateinit var buttonGuardar: Button

    @FXML
    private fun initialize() {
        gestionCitaViewModel.state.addListener { _, _, newValue ->
            initForm(newValue)
        }
        textMatricula.setOnKeyReleased {
            gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(matricula = textMatricula.text)
        }
        textEspecialidad.setOnHidden {
            gestionCitaViewModel.asignarTrabajador(textEspecialidad.value)
            textTrabajador.value = ""
        }
        textTrabajador.setOnHidden {
            gestionCitaViewModel.asignarEspecialidad(textTrabajador.value)
        }
        textFecha.setOnAction {
            if (textFecha.value != null) gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(fecha = textFecha.value.toString())
        }
        textHora.setOnHidden {
            gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(horaSelected = textHora.value)
        }
        buttonGuardar.setOnAction {
            if (gestionCitaViewModel.guardarCita()) {
                val stage = buttonGuardar.scene.window

                textMatricula.text = ""
                textFecha.value = null

                if (stage is Stage) {
                    showInfoAlert("Cita guardada con éxito")
                    stage.close()
                }
            }
        }
    }

    private fun initForm(newValue: GestionCitaState) {
        logger.debug { "GestionCitaController -> Generando ficha cita" }
        if (newValue.typeAction == TypeAction.NEW) {
            textEspecialidad.items = FXCollections.observableArrayList(newValue.especialidad)
            if (newValue.especialidadSelected != "") textEspecialidad.value = newValue.especialidadSelected
            textTrabajador.items = FXCollections.observableArrayList(newValue.trabajador)
            if (newValue.trabajadorSelected != "") textTrabajador.value = newValue.trabajadorSelected
            textHora.items = FXCollections.observableArrayList(newValue.hora)
            textHora.value = newValue.horaSelected
        } else if (newValue.typeAction == TypeAction.EDIT) {
            textEspecialidad.items = FXCollections.observableArrayList(newValue.especialidad)
            textEspecialidad.value = newValue.especialidadSelected
            textTrabajador.items = FXCollections.observableArrayList(newValue.trabajador)
            textTrabajador.value = newValue.trabajadorSelected
            textFecha.value = LocalDate.parse(newValue.fecha.substring(0, 10))
            textHora.items = FXCollections.observableArrayList(newValue.hora)
            textHora.value = newValue.horaSelected
        }
    }

}