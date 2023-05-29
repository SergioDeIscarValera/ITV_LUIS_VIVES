package com.example.itv_citas.controllers

import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.route.RoutesManager.getResourceAsStream
import com.example.itv_citas.route.RoutesManager.showInfoAlert
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

    @FXML
    private lateinit var correo_field: TextField

    @FXML
    private lateinit var password_field: TextField

    @FXML
    private lateinit var inicio_button: Button

    @FXML
    private lateinit var olvidar_button: Button

    /*@FXML
    private lateinit var password_button: Button

    @FXML
    private lateinit var imageBotonPassword: Image*/


    fun initialize() {
        initBindings()
        initEventos()
    }


    private fun initBindings() {
        logger.debug { "PrincipalController -> \tinitBindings" }

        viewModel.state.addListener { _, _, newValue ->
            correo_field.text = newValue.usuario
            //password_field.text = if (newValue.hidePassword) viewModel.hidePassword() else newValue.password
            password_field.text = newValue.password
        }
    }

    private fun initEventos() {
        logger.debug { "PrincipalController -> \tinitEventos" }
        correo_field.setOnKeyReleased {
            viewModel.state.value = viewModel.state.value.copy(usuario = correo_field.text)
        }
        password_field.setOnKeyReleased {
            viewModel.state.value = viewModel.state.value.copy(
                password = password_field.text,
            )
            password_field.positionCaret(password_field.text.length)
        }
        eventsButtons()
    }

    private fun eventsButtons() {
        inicio_button.setOnAction {
            viewModel.checkCredentials()
        }

        /*password_button.setOnAction {
            viewModel.state.value = viewModel.state.value.copy(hidePassword = !viewModel.state.value.hidePassword)
            imageBotonPassword = if (viewModel.state.value.hidePassword){
                Image(getResourceAsStream("images/ojo_cerrado.png"))
            }else{
                Image(getResourceAsStream("images/ojo_abierto.png"))
            }
            password_field.positionCaret(password_field.text.length)
        }*/

        olvidar_button.setOnAction {
            showInfoAlert("Lo sentimos, pero cagaste. No hay nada que hacer chao :)")
        }
    }
}