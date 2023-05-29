package com.example.itv_citas.viewmodels


import com.example.itv_citas.repositories.employee.EmployeeRepository
import com.example.itv_citas.route.RoutesManager
import com.example.itv_citas.states.ForgotPasswordState
import com.github.michaelbull.result.get
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ForgotPasswordModel: KoinComponent {

    private val repository by inject<EmployeeRepository>(named("EmployeeBBDD"))
    val state: ObjectProperty<ForgotPasswordState> = SimpleObjectProperty(ForgotPasswordState())


    fun showPassword(email:String):String{

        if ( repository.findByEmail(email).get()?.email != null ){
            return repository.findByEmail(email).get()!!.password
        }else RoutesManager.showErrorAlert("Correo no valido","El correo introducido no existe.")
        return ""
    }
}