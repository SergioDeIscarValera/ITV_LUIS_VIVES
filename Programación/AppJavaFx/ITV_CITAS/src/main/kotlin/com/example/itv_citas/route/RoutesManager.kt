package com.example.itv_citas.route

import com.example.javafxdemo.routes.Views
import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.ChoiceDialog
import javafx.scene.image.Image
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.InputStream
import java.net.URL


object RoutesManager {
    lateinit var app: Application

    private val screenMap = mutableMapOf<Views, Scene>()
    private lateinit var stage: Stage

    fun setStage(stage: Stage){
        stage.setOnCloseRequest {
            exitAlert()
            it.consume()
        }

        RoutesManager.stage = stage
    }

    fun addScreen(
        views: Views
    ) {
        screenMap[views] = Scene(
            FXMLLoader.load(getResource(views.fxml)),
            views.size.first,
            views.size.second
        )
    }

    fun removeScreen(view: Views) {
        screenMap.remove(view)
    }

    fun changeScene(
        view: Views,
        title: String,
        urlIcon: String = "images/icon.png"
    ) {
        val scene = screenMap[view] ?: return
        stage.title = title
        stage.scene = scene
        stage.icons.add(
            Image(
                getResourceAsStream(urlIcon)
            )
        )
        stage.isResizable = false
        stage.show()
    }

    fun openModal(
        view: Views,
        title: String,
        urlIcon: String = "images/icon.png"
    ) {
        val scene = screenMap[view] ?: return
        Stage().apply {
            this.title = title
            this.scene = scene
            this.icons.add(
                Image(
                    getResourceAsStream(urlIcon)
                )
            )
            this.initOwner(stage)
            this.initModality(Modality.WINDOW_MODAL)
            this.isResizable = false
        }.show()
    }

    fun exitAlert(){
        Alert(Alert.AlertType.CONFIRMATION).apply {
            title = "Salir"
            headerText = "¿Estás seguro que quieres salir?"
        }.showAndWait().filter { it == ButtonType.OK }.ifPresent { Platform.exit() }
    }

    fun choiceDialog(
        actions: Map<String, (String) -> Unit>,
        title: String,
        header: String,
        content: String,
        typeChoose: TypeChoose,
        extensions: String = "",
    ) {
        val values = actions.keys.toList()
        ChoiceDialog(values[0], values).apply {
            this.title = title
            headerText = header
            contentText = content
        }.showAndWait().ifPresent { selectedValue ->
            when(typeChoose){
                TypeChoose.FILE -> fileChoose("Seleccionar archivo", extensions)
                TypeChoose.FOLDER -> folderChoose()
            }?.let {
                actions[selectedValue]?.invoke(it)
                return@ifPresent
            }
            showErrorAlert("Error", "No se ha seleccionado ningún archivo/directorio")
        }
    }

    fun fileChoose(
        title: String,
        extensions: String,
    ): String?{
        val fileChooser = FileChooser()

        fileChooser.title = title

        val filter = FileChooser.ExtensionFilter("Archivos de tipo $extensions", extensions)
        fileChooser.extensionFilters.add(filter)

        val selectedFile = fileChooser.showOpenDialog(stage)
        return selectedFile?.absolutePath
    }

    fun folderChoose(): String?{
        val directoryChooser = DirectoryChooser()

        // Establecer el título del diálogo
        directoryChooser.title = "Seleccionar carpeta de destino"

        // Mostrar el diálogo de selección de directorios y obtener la carpeta seleccionada
        val selectedFolder = directoryChooser.showDialog(stage)
        return selectedFolder?.absolutePath
    }

    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("Resource $resource not found")
    }

    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("Resource $resource not found")
    }

    fun addScreens(views: Array<Views>) {
        views.forEach {
            addScreen(it)
        }
    }

    fun showErrorAlert(headerText: String, contentText: String?) {
        Alert(Alert.AlertType.ERROR).apply {
            title = "Error"
            this.headerText = headerText
            this.contentText = contentText
        }.showAndWait()
    }

    fun showInfoAlert(headerText: String) {
        Alert(Alert.AlertType.INFORMATION).apply {
            title = "Información"
            this.headerText = headerText
        }.showAndWait()
    }
}

enum class TypeChoose{
    FILE, FOLDER
}