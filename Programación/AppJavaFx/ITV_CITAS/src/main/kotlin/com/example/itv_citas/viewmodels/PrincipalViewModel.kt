package com.example.itv_citas.viewmodels


import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.states.PrincipalState
import com.github.michaelbull.result.get
import com.github.michaelbull.result.mapBoth
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.TextField
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named


class PrincipalViewModel: KoinComponent {

    private val repository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    val state: ObjectProperty<PrincipalState> = SimpleObjectProperty(PrincipalState())



    private var textOriginal =""

//    fun checkPassword(password: String):Boolean{
//
//        val isPasswordCorrect = BCrypt.checkpw( password,repository.findByUser(state.value.usuario).get()!!.password)
//
//       return isPasswordCorrect
//
//    }

    fun checkPassword(password: String):Boolean{
        return (password==repository.findByUser(state.value.usuario).get()!!.password)
    }

    fun hidePassword(correoField: TextField): String {
        textOriginal = state.value.password
        return "*".repeat(textOriginal.length)
    }





    fun checkCredentials():Boolean{
        var correct = false
        repository.findByUser(state.value.usuario).mapBoth(
            success = {
                if (checkPassword(state.value.password)){
                    correct= true
                }else{

                    RoutesManager.showErrorAlert("Credenciales no Validas","La contraseña introducida no es válida.")
                }
            },
            failure = {
                RoutesManager.showErrorAlert("Credenciales no Validas","El usuario introducido no es válido.")
            }

        )
            return correct
        }
    }
