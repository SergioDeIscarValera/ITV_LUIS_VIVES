package default_values

import models.Vehicle
import models.enums.TypeMotor
import models.enums.TypeVehicle
import java.time.LocalDate
import java.time.LocalDateTime

object DefaultValueVehicle {
    fun defaultValueVehicle() = listOf(
        Vehicle("5421GVJ", "Alfa Romeo", "Stelvio", TypeVehicle.valueOf("TURISMO"), TypeMotor.valueOf("GASOLINA"), LocalDate.parse("2017-02-25"), LocalDateTime.parse("2022-06-23T12:47:29"), "54875241R"),
        Vehicle("2385KLP", "Ford", "Transit", TypeVehicle.valueOf("FURGONETA"), TypeMotor.valueOf("HIBRIDO"), LocalDate.parse("2019-08-12"), LocalDateTime.parse("2023-04-15T10:52:02"), "54875241R"),
        Vehicle("7843FBD", "Mercedes-Benz", "Actros", TypeVehicle.valueOf("CAMION"), TypeMotor.valueOf("DIESEL"), LocalDate.parse("2016-05-10"), LocalDateTime.parse("2021-11-30T11:44:56"), "51487524T"),
    )
}