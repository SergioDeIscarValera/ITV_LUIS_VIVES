package com.example.itv_citas.controllers

import com.example.itv_citas.viewmodels.ForgotPasswordModel
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


private val logger = KotlinLogging.logger {  }

class ForgotPasswordController: KoinComponent {

    private val viewModel by inject<ForgotPasswordModel>()

    @FXML
    private lateinit var userField: TextField

    @FXML
    private lateinit var passwordField: TextField

    fun initialize() {
        initBindings()
        initEventos()
    }



    private fun initBindings() {
        logger.debug { "ForgotPasswordController -> \tinitBindings" }

        userField.setOnKeyReleased {
            viewModel.state.value = viewModel.state.value.copy(correo = userField.text)
        }
    }

    private fun initEventos() {
        logger.debug { "ForgotPasswordController -> \tinitEventos" }

        userField.setOnKeyPressed { event -> if (event.code == KeyCode.ENTER){
            passwordField.text = viewModel.showPassword(userField.text)
        }
        }
    }
}