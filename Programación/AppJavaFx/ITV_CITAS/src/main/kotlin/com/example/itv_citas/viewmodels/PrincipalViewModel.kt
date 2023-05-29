package com.example.itv_citas.viewmodels

import com.example.itv_citas.errors.EmployeeError
import com.example.itv_citas.models.Employee
import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.route.RoutesManager.changeScene
import com.example.itv_citas.route.RoutesManager.showErrorAlert
import com.example.itv_citas.states.PrincipalState
import com.example.javafxdemo.routes.Views
import com.github.michaelbull.result.*
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.TextField
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.mindrot.jbcrypt.BCrypt

class PrincipalViewModel: KoinComponent {

    private val repository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    val state: ObjectProperty<PrincipalState> = SimpleObjectProperty(PrincipalState())

    fun hidePassword(): String {
        val stringBuilder = StringBuilder()
        repeat(state.value.password.length) {
            stringBuilder.append("*")
        }
        return stringBuilder.toString()
    }

    fun checkCredentials(){

        repository.findByUser(state.value.usuario)
            .andThen { checkPassword(state.value.password, it.password) }
            .mapBoth(
                success = {
                    changeScene(Views.HOME, "ITV Cita")
                },
                failure = {
                    when(it){
                        is EmployeeError.InvalidPassword -> {
                            showErrorAlert("Error", "Password is not correct")
                        }
                        is EmployeeError.EmployeeNotFound -> {
                            showErrorAlert("Error", "Employee not found")
                        }
                        is EmployeeError.UserNotFound -> {
                            showErrorAlert("Error", "User not found")
                        }
                        else -> {
                            showErrorAlert("Error", "Error desconocido")
                        }
                    }
                }
            )
    }

    // El driver ya se encarga del desencriptado...
    /*private fun checkPassword(password: String, passwordHashed: String): Result<Employee, EmployeeError>{
        return when{
            !BCrypt.checkpw(password, passwordHashed) -> Err(EmployeeError.InvalidPassword)
            else -> Ok(repository.findByUser(state.value.usuario).get()!!)
        }
    }*/
    private fun checkPassword(password: String, passwordHashed: String): Result<Employee, EmployeeError>{
        return when{
            password != passwordHashed -> Err(EmployeeError.InvalidPassword)
            else -> Ok(repository.findByUser(state.value.usuario).get()!!)
        }
    }
}
