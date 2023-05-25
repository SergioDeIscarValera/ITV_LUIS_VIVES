package repositories.vehicle

import errors.VehicleError
import models.Vehicle
import models.enums.TypeMotor
import models.enums.TypeVehicle
import services.database.DataBaseManager
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
        val sql = """SELECT * FROM tVehiculo"""
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
        var vehicle: Vehicle? = null
        val sql = """SELECT * FROM tVehiculo WHERE cMatricula = ?"""
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
        result.getString("cMatricula"),
        result.getString("cMarca"),
        result.getString("cModelo"),
        TypeVehicle.valueOf(result.getString("cTipoVehiculo")),
        TypeMotor.valueOf(result.getString("cTipoMotor")),
        result.getDate("dFecha_Matriculacion").toLocalDate(),
        result.getTimestamp("dFecha_UltimaRevision").toLocalDateTime(),
        result.getString("cDniPropietario")
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