package default_values

import default_values.DefaultValueSpeciality.administracion
import default_values.DefaultValueSpeciality.electricidad
import default_values.DefaultValueSpeciality.motor
import models.Employee

object DefaultValueEmployee {

    fun defaultValueEmployee() = listOf(
        Employee(1, "Sonia", "soniagomez@gmail.com", "soniagomez", "625874952", "", administracion, 1, 1),
        Employee(2, "Pablo", "pablomesas@hotmail.com", "pablomesas", "652256984", "", electricidad, 1, 1),
        Employee(3, "Rocio", "rociofuente@gmail.com", "rociofuente", "696658974", "", motor, 1, 1))

}