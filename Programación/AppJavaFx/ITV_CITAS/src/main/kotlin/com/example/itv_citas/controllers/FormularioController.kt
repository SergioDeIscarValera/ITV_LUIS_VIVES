package com.example.itv_citas.controllers

import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.route.TypeChoose
import com.example.itv_citas.states.FormularioState
import com.example.itv_citas.viewmodels.FormViewModel
import javafx.fxml.FXML
import javafx.scene.control.*
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val logger = KotlinLogging.logger{}

class FormularioController: KoinComponent {

    private val formViewModel by inject<FormViewModel>()

    private val exportAlertMessage = Pair("Seleccione el tipo de exportación", "Tipo de exportación:")

    //Datos del formulario

    @FXML
    private lateinit var textFecha: TextField

    @FXML
    private lateinit var textVehiculo: TextField

    @FXML
    private lateinit var textPropietario: TextField

    @FXML
    private lateinit var textTrabajador: TextField

    @FXML
    private lateinit var spinnerFrenado: Spinner<Double>

    @FXML
    private lateinit var spinnerContaminacion: Spinner<Double>

    @FXML
    private lateinit var checkBoxInterior: CheckBox

    @FXML
    private lateinit var checkBoxLuces: CheckBox

    @FXML
    private lateinit var checkBoxApto: CheckBox

    @FXML
    private lateinit var buttonGuardar: Button

    @FXML
    private fun initialize() {
        checkBoxApto.isDisable = true
        spinnerFrenado.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 10.0, 5.0, 0.05)
        formViewModel.state.value = formViewModel.state.value.copy(frenado = spinnerFrenado.value.toString())
        spinnerContaminacion.valueFactory = SpinnerValueFactory.DoubleSpinnerValueFactory(20.0, 50.0, 35.0, 0.05)
        formViewModel.state.value = formViewModel.state.value.copy(contaminacion = spinnerContaminacion.value.toString())
        formViewModel.state.addListener { _, _, newValue ->
            initForm(newValue)
        }
        initEventos()
    }

    private fun initForm(state: FormularioState) {
        textFecha.text = state.fecha
        textVehiculo.text = state.vehiculo
        textPropietario.text = state.propietario
        textTrabajador.text = state.trabajador
        checkBoxApto.isDisable = !(state.interior && state.luces)
        checkBoxApto.isSelected = state.apto
    }

    private fun initEventos() {
        logger.debug { "FormController -> Iniciando los eventos" }
        spinnerFrenado.setOnMouseReleased {
            formViewModel.state.value = formViewModel.state.value.copy(frenado = spinnerFrenado.value.toString())
        }
        spinnerContaminacion.setOnMouseReleased {
            formViewModel.state.value = formViewModel.state.value.copy(contaminacion = spinnerContaminacion.value.toString())
        }
        checkBoxInterior.setOnAction {
            formViewModel.state.value = formViewModel.state.value.copy(interior = checkBoxInterior.isSelected)
            checkApto()
        }
        checkBoxLuces.setOnAction {
            formViewModel.state.value = formViewModel.state.value.copy(luces = checkBoxLuces.isSelected)
            checkApto()
        }
        checkBoxApto.setOnAction {
            formViewModel.state.value = formViewModel.state.value.copy(apto = checkBoxApto.isSelected)
        }
        buttonGuardar.setOnAction {
            if (!formViewModel.validarDatos()) buttonGuardar.scene.window.hide()
            RoutesManager.choiceDialog(
                mapOf(
                    "JSON" to { path -> formViewModel.exportInformeToJson(path) },
                    "HTML" to { path -> formViewModel.exportarInformeToHtml(path) }
                ),
                "Exportar cita:",
                exportAlertMessage.first,
                exportAlertMessage.second,
                TypeChoose.FOLDER
            )
            buttonGuardar.scene.window.hide()
        }
    }

    private fun checkApto(){
        checkBoxApto.isDisable = !(checkBoxInterior.isSelected && checkBoxLuces.isSelected)
        if (checkBoxApto.isDisable) formViewModel.state.value = formViewModel.state.value.copy(
            apto = false
        )
    }

}