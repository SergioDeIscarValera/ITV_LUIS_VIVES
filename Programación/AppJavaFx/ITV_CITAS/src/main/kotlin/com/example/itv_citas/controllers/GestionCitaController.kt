package com.example.itv_citas.controllers

import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.example.itv_citas.route.RoutesManager.showInfoAlert
import com.example.itv_citas.states.TypeActionView
import com.example.itv_citas.viewmodels.GestionCitaViewModel
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate

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
    private fun initialize(){
        // Binding
        initBinding()
        // Eventos
        initEventos()
    }

    private fun initBinding(){
        gestionCitaViewModel.state.addListener { _, _, newValue ->
            textMatricula.text = newValue.matricula
            textEspecialidad.items = FXCollections.observableArrayList(newValue.especialidades)
            textTrabajador.items = FXCollections.observableArrayList(newValue.trabajadores)
            textTrabajador.value = newValue.trabajadorSelected
            if (newValue.fecha.trim().isNotBlank())
                textFecha.value = LocalDate.parse(newValue.fecha)
            else textFecha.value = null
            textHora.items = FXCollections.observableArrayList(newValue.hora)
            textHora.value = newValue.horaSelected
        }
    }

    private fun initEventos(){
        textMatricula.setOnKeyReleased {
            gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(matricula = textMatricula.text)
            textMatricula.positionCaret(textMatricula.text.length)
        }
        textEspecialidad.setOnAction {
            gestionCitaViewModel.filtrarPorEspecialidad(textEspecialidad.value ?: "")
        }
        textTrabajador.setOnAction {
            if (textTrabajador.value == null) return@setOnAction
            gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(trabajadorSelected = textTrabajador.value)
        }
        textFecha.setOnAction {
            if (textFecha.value == null ||
                (textFecha.value.isBefore(LocalDate.now()) && gestionCitaViewModel.state.value.typeAction == TypeActionView.NEW)) {
                showErrorAlert("Fecha invalida", "La fecha no puede ser anterior a la actual")
                textFecha.value = LocalDate.now()
                return@setOnAction
            }
            gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(fecha = textFecha.value.toString())
        }
        textHora.setOnHidden {
            gestionCitaViewModel.state.value = gestionCitaViewModel.state.value.copy(horaSelected = textHora.value)
        }
        buttonGuardar.setOnAction {
            if (gestionCitaViewModel.guardarCita()) {
                showInfoAlert("Cita guardada con éxito")
                gestionCitaViewModel.updateMainView()
                buttonGuardar.scene.window.hide()
            }
        }
    }
}