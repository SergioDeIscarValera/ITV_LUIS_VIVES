package com.example.itv_citas.controllers

import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.viewmodels.PrincipalViewModel
import com.example.javafxdemo.routes.Views
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.Image
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


private val logger = KotlinLogging.logger {  }

class PrincipalController : KoinComponent {


    private val viewModel by inject<PrincipalViewModel>()
    private var visiblePassword = true

    @FXML
    private lateinit var correo_field: TextField

    @FXML
    private lateinit var password_field: TextField

    @FXML
    private lateinit var inicio_button: Button

    @FXML
    private lateinit var olvidar_button: Button

    @FXML
    private lateinit var password_button: Button

    @FXML
    private lateinit var imageBotonPassword: Image


    fun initialize() {
        initBindings()
        initEventos()
    }


    private fun initBindings() {
        logger.debug { "PrincipalController -> \tinitBindings" }

        correo_field.setOnKeyReleased {
            viewModel.state.value = viewModel.state.value.copy(usuario = correo_field.text)
        }
        password_field.setOnKeyReleased {
            viewModel.state.value = viewModel.state.value.copy(password = password_field.text)
        }
    }

    private fun initEventos() {
        logger.debug { "PrincipalController -> \tinitEventos" }

        eventsButtons()
    }
    //password_field.text=viewModel.hideUnhidePassword()
    private fun eventsButtons() {

        inicio_button.setOnAction {

            if (viewModel.checkCredentials()){
                RoutesManager.changeScene(Views.HOME, "Home")
            }else password_field.text = ""
        }


        password_button.setOnAction {

            imageBotonPassword = Image(RoutesManager.getResourceAsStream("images/ojo_abierto.png"))
            visiblePassword=!visiblePassword

            if (visiblePassword){
                correo_field.text=viewModel.state.value.usuario

            }else correo_field.setOnKeyReleased {correo_field.text = viewModel.hidePassword(correo_field) }

        }

        olvidar_button.setOnAction { RoutesManager.openModal(Views.FORGOT_PASSWORD,"Recuperar Contraseña") }
    }
}