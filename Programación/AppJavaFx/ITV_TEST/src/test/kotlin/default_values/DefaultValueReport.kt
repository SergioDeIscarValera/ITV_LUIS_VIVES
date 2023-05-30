package default_values

import default_values.DefaultValueEmployee.defaultValueEmployee
import default_values.DefaultValueOwner.defaultValueOwner
import default_values.DefaultValueVehicle.defaultValueVehicle
import models.Report

object DefaultValueReport {

    fun defaultValueReport() = listOf(
        Report(1, true, 20.0, 40.0, false, true, defaultValueEmployee()[0], defaultValueOwner()[0].vehicles[0], defaultValueOwner()[0]),
        Report(2, false, 25.0, 70.0, true, false, defaultValueEmployee()[1], defaultValueOwner()[1].vehicles[0], defaultValueOwner()[1])
    )

}