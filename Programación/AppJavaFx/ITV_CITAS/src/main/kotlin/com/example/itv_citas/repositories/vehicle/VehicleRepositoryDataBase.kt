package com.example.itv_citas.repositories.vehicle

import com.example.itv_citas.errors.VehicleError
import com.example.itv_citas.models.Vehicle
import com.example.itv_citas.models.enums.TypeMotor
import com.example.itv_citas.models.enums.TypeVehicle
import com.example.itv_citas.services.database.DataBaseManager
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.ResultSet

private val logger = KotlinLogging.logger {}

class VehicleRepositoryDataBase: VehicleRepository, KoinComponent {
    private val dataBaseManager by inject<DataBaseManager>()

    override fun findAll(): Iterable<Vehicle> {
        logger.debug { "VehicleRepositoryDataBase ->\tfindAll" }
        val vehicles = mutableListOf<Vehicle>()
        val sql = """SELECT * FROM tVehicles"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                vehicles.add(
                    resultToVehicle(result)
                )
            }
        }
        return vehicles.toList()
    }

    override fun findById(id: String): Result<Vehicle, VehicleError> {
        logger.debug { "VehicleRepositoryDataBase ->\tfindById" }
        var vehicle:Vehicle? = null
        val sql = """SELECT * FROM tVehicles WHERE cCarNumber = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                vehicle = resultToVehicle(result)
            }
        }
        return if (vehicle != null) Ok(vehicle!!) else Err(VehicleError.VehicleNotFound)
    }

    private fun resultToVehicle(result: ResultSet) = Vehicle(
        result.getString("cCarNumber"),
        result.getString("cBrand"),
        result.getString("cModel"),
        TypeVehicle.valueOf(result.getString("cTypeVehicle")),
        TypeMotor.valueOf(result.getString("cTypeMotor")),
        result.getDate("dRegistrationDate").toLocalDate(),
        result.getTimestamp("dDateITV").toLocalDateTime(),
        result.getString("cDniOwner")
    )

    override fun existsById(id: String): Boolean {
        logger.debug { "VehicleRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        logger.debug { "VehicleRepositoryDataBase ->\tcount" }
        return findAll().count()
    }
}