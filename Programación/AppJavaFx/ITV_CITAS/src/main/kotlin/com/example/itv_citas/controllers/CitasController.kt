package com.example.itv_citas.controllers

import com.example.itv_citas.models.Appointment
import com.example.itv_citas.models.enums.TypeVehicle
import com.example.itv_citas.states.TypeActionView
import com.example.itv_citas.viewmodels.CitaViewModel
import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.route.RoutesManager.choiceDialog
import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.example.itv_citas.route.TypeChoose
import com.example.javafxdemo.routes.Views
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate

private val logger = KotlinLogging.logger {  }

class CitasController: KoinComponent {
    private val viewModel by inject<CitaViewModel>()

    private val exportAlertMessage = Pair("Seleccione el tipo de exportación", "Tipo de exportación:")
    private val importAlertMessage = Pair("Seleccione el tipo de importación", "Tipo de importación:")

    // Menus
    @FXML
    private lateinit var manuCerrarSesion: MenuItem
    @FXML
    private lateinit var menuSalir: MenuItem

    @FXML
    private lateinit var menuExportarCitas: MenuItem
    @FXML
    private lateinit var menuExportarTrabajadores: MenuItem

    @FXML
    private lateinit var menuImportarCitas: MenuItem
    @FXML
    private lateinit var menuImportarTrabajadores: MenuItem

    // TextFields/Labels
    //region Vehicle
    @FXML
    private lateinit var textMatricula: Label
    @FXML
    private lateinit var textMarca: TextField
    @FXML
    private lateinit var textTipoVehicle: TextField
    @FXML
    private lateinit var textDateMatriculacion: TextField
    @FXML
    private lateinit var textModelo: TextField
    @FXML
    private lateinit var textMotor: TextField
    @FXML
    private lateinit var textUltimaRevision: TextField
    //endregion
    //region Owner
    @FXML
    private lateinit var textDni: Label
    @FXML
    private lateinit var textNombre: TextField
    @FXML
    private lateinit var textApellido: TextField
    @FXML
    private lateinit var textTelefono: TextField
    @FXML
    private lateinit var textCorreo: TextField
    //endregion
    @FXML
    private lateinit var textFilter: TextField

    // Buttons
    @FXML
    private lateinit var buttonNuevo: Button
    @FXML
    private lateinit var buttonEditar: Button
    @FXML
    private lateinit var buttonBorrar: Button
    @FXML
    private lateinit var buttonExportarCita: Button
    @FXML
    private lateinit var buttonCrearInforme: Button
    @FXML
    private lateinit var buttonClearDate: Button

    //Dropdown
    @FXML
    private lateinit var choiceTipo: ChoiceBox<String>

    //DatePicker
    @FXML
    private lateinit var dateInicial: DatePicker
    @FXML
    private lateinit var dateFinal: DatePicker

    //TableView
    @FXML
    private lateinit var tableCitas: TableView<Appointment>
    @FXML
    private lateinit var columnFecha: TableColumn<Appointment, String>
    @FXML
    private lateinit var columnTrabajador: TableColumn<Appointment, String>
    @FXML
    private lateinit var columnMatricula: TableColumn<Appointment, String>

    // Images
    @FXML
    private lateinit var imageVehicle: ImageView

    @FXML
    private fun initialize(){
        // Binding
        initBinding()
        // Eventos
        initEventos()
    }

    private fun initBinding() {
        logger.debug { "CitasController ->\tinitBinding" }
        //Dropdown
        choiceTipo.items = FXCollections.observableArrayList(
            viewModel.state.value.tipos
        )
        choiceTipo.selectionModel.selectFirst()

        //TableView
        tableCitas.items = FXCollections.observableArrayList(viewModel.state.value.citas)
        tableCitas.selectionModel.selectionMode = SelectionMode.SINGLE

        // TableColumns
        columnFecha.cellValueFactory = PropertyValueFactory("date")
        columnTrabajador.cellValueFactory = PropertyValueFactory("idEmployee")
        columnMatricula.cellValueFactory = PropertyValueFactory("carNumber")

        //TextFild
        viewModel.state.addListener { _, oldState, newState ->
            if (oldState == newState) return@addListener
            tableCitas.items = FXCollections.observableArrayList(newState.citas)
            //region Vehicle
            textMatricula.text = newState.matriculaVehicle
            textMarca.text = newState.marcaVehicle
            textTipoVehicle.text = newState.tipoVehicle
            textDateMatriculacion.text = newState.fechaMatriculacionVehicle
            textModelo.text = newState.modeloVehicle
            textMotor.text = newState.motorVehicle
            textUltimaRevision.text = newState.fehcaUltimaRevisionVehicle
            //endregion
            //region Owner
            textDni.text = newState.dniOwner
            textNombre.text = newState.nombreOwner
            textApellido.text = newState.apellidoOwner
            textTelefono.text = newState.telefonoOwner
            textCorreo.text = newState.correoOwner
            //endregion
            imageVehicle.image = Image(
                when(newState.tipoVehicle){
                    TypeVehicle.CAMION.value -> RoutesManager.getResourceAsStream("images/camion.png")
                    TypeVehicle.FURGONETA.value -> RoutesManager.getResourceAsStream("images/furgoneta.png")
                    TypeVehicle.MOTOCICLETA.value -> RoutesManager.getResourceAsStream("images/moto.png")
                    else -> RoutesManager.getResourceAsStream("images/coche.png")
                }
            )
        }
    }

    private fun initEventos() {
        logger.debug { "CitasController ->\tinitEventos" }
        // Menu
        eventsMenu()

        // Dropdown
        choiceTipo.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { filterDataTable() }
        }

        // TextField
        textFilter.setOnKeyReleased { newValue ->
            newValue?.let { filterDataTable() }
        }

        // TableView
        tableCitas.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTableSelected(it) }
        }

        // Buttons
        eventsButton()

        // DatePicker
        dateInicial.valueProperty().addListener { _, oldValue, newValue ->
            checkDatePicker(newValue, oldValue, dateInicial, dateFinal)
        }
        dateFinal.valueProperty().addListener { _, oldValue, newValue ->
            checkDatePicker(newValue, oldValue, dateFinal, dateInicial)
        }
    }

    private fun checkDatePicker(
        newValue: LocalDate?,
        oldValue: LocalDate?,
        myDatePicker: DatePicker,
        otherDatePicker: DatePicker
    ){
        if (newValue != null && otherDatePicker.value != null) {
            if (!checkDate(dateInicial.value, dateFinal.value)) {
                showErrorAlert("Fecha invalida", "La fecha final no puede ser anterior a la fecha inicial")
                myDatePicker.value = oldValue
                return
            }
            filterDataTable()
        }
    }

    private fun checkDate(inicial: LocalDate, final: LocalDate): Boolean{
        return inicial.isBefore(final) && final.isAfter(inicial)
    }

    private fun eventsButton() {
        buttonNuevo.setOnAction {
            viewModel.state.value = viewModel.state.value.copy(
                typeAction = TypeActionView.NEW
            )
            RoutesManager.openModal(
                Views.FORM_CITAS,
                "Nueva cita"
            )
        }

        buttonEditar.setOnAction {
            if (viewModel.state.value.matriculaVehicle.trim().isBlank()) return@setOnAction
            viewModel.state.value = viewModel.state.value.copy(
                typeAction = TypeActionView.EDIT
            )
            RoutesManager.openModal(
                Views.FORM_CITAS,
                "Editar cita"
            )
        }

        buttonBorrar.setOnAction {
            if (viewModel.state.value.matriculaVehicle.trim().isBlank()) return@setOnAction
            Alert(Alert.AlertType.CONFIRMATION).apply {
                title = "Borrar cita"
                headerText = "¿Está seguro de que desea borrar la cita?"
                contentText = "Esta acción no se puede deshacer"
                showAndWait().ifPresent {
                    if (it == ButtonType.OK) {
                        viewModel.deleteCitaSelected()
                    }
                }
            }
        }

        buttonCrearInforme.setOnAction {
            if (viewModel.state.value.matriculaVehicle.trim().isBlank()) return@setOnAction
            RoutesManager.openModal(
                Views.FORM_INFORMES,
                "Crear informe"
            )
        }

        buttonExportarCita.setOnAction {
            if (viewModel.state.value.matriculaVehicle.trim().isBlank()) return@setOnAction
            choiceDialog(
                mapOf(
                    "JSON" to { path -> viewModel.exportCitaToJson(path) }
                ),
                "Exportar cita:",
                exportAlertMessage.first,
                exportAlertMessage.second,
                TypeChoose.FOLDER
            )
        }

        buttonCrearInforme.setOnAction {
            if (viewModel.state.value.matriculaVehicle.trim().isBlank()) return@setOnAction
            RoutesManager.openModal(
                Views.FORM_INFORMES,
                "Crear informe"
            )
        }

        buttonClearDate.setOnAction {
            dateInicial.value = null
            dateFinal.value = null
            filterDataTable()
        }
    }

    private fun onTableSelected(it: Appointment) {
        viewModel.updateCitaSelected(it)
    }

    private fun filterDataTable() {
        viewModel.citasFilteredList(choiceTipo.value, textFilter.text.trim(), Pair(dateInicial.value, dateFinal.value))
    }

    private fun eventsMenu(){
        menuSalir.setOnAction {
            RoutesManager.exitAlert()
        }
        manuCerrarSesion.setOnAction {
            Alert(Alert.AlertType.CONFIRMATION).apply {
                title = "Cerrar sesión"
                headerText = "¿Está seguro de que desea cerrar sesión?"
                contentText = ""
                showAndWait().ifPresent {
                    if (it == ButtonType.OK) {
                        RoutesManager.changeScene(
                            Views.LOGIN,
                            "Login"
                        )
                    }
                }
            }
        }
        menuExportarCitas.setOnAction {
            choiceDialog(
                mapOf(
                    "JSON" to { path -> viewModel.exportCitasToJson(path) }
                ),
                "Exportar citas:",
                exportAlertMessage.first,
                exportAlertMessage.second,
                TypeChoose.FOLDER
            )
        }
        menuExportarTrabajadores.setOnAction {
            choiceDialog(
                mapOf(
                    "CSV" to { path -> viewModel.exportTrabajadoresCsv(path) }
                ),
                "Exportar trabajadores:",
                exportAlertMessage.first,
                exportAlertMessage.second,
                TypeChoose.FOLDER
            )
        }
        menuImportarCitas.setOnAction {
            choiceDialog(
                mapOf(
                    "JSON" to { path -> viewModel.importCitasFromJson(path) }
                ),
                "Importar citas:",
                importAlertMessage.first,
                importAlertMessage.second,
                TypeChoose.FILE,
                "*.json"
            )
        }
        menuImportarTrabajadores.setOnAction {
            choiceDialog(
                mapOf(
                    "CSV" to { path -> viewModel.importTrabajadoresFromCsv(path) }
                ),
                "Importar trabajadores:",
                importAlertMessage.first,
                importAlertMessage.second,
                TypeChoose.FILE,
                "*.csv"
            )
        }
    }
}