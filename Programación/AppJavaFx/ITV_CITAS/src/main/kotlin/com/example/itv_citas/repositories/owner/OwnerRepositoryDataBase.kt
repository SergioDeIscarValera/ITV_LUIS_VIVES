package com.example.itv_citas.repositories.owner

import com.example.itv_citas.errors.OwnerError
import com.example.itv_citas.models.Owner
import com.example.itv_citas.repositories.vehicle.VehicleRepository
import com.example.itv_citas.services.database.DataBaseManager
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.sql.ResultSet

private val logger = KotlinLogging.logger {}

class OwnerRepositoryDataBase: OwnerRepository, KoinComponent {
    private val dataBaseManager by inject<DataBaseManager>()
    private val vehicleRepository by inject<VehicleRepository>(named("VehicleBBDD"))

    override fun findAll(): Iterable<Owner> {
        logger.debug { "OwnerRepositoryDataBase ->\tfindAll" }
        val owners = mutableListOf<Owner>()
        val sql = """SELECT * FROM tPropietario"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                owners.add(
                    resultToOwner(result)
                )
            }
        }
        return owners.toList()
    }

    override fun findById(id: String): Result<Owner, OwnerError> {
        logger.debug { "OwnerRepositoryDataBase ->\tfindById" }
        var owner:Owner? = null
        val sql = """SELECT * FROM tPropietario WHERE cDNI = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                owner = resultToOwner(result)
            }
        }
        return if (owner != null) Ok(owner!!) else Err(OwnerError.OwnerNotFound)
    }

    private fun resultToOwner(result: ResultSet) = Owner(
        result.getString("cDNI"),
        result.getString("cNombre"),
        result.getString("cApellidos"),
        result.getString("cCorreoElectronico"),
        result.getString("cTelefono"),
        vehicleRepository.findAll().filter { it.dniOwner == result.getString("cDNI") }.toList()
    )

    override fun existsById(id: String): Boolean {
        logger.debug { "OwnerRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        logger.debug { "OwnerRepositoryDataBase ->\tcount" }
        return findAll().count()
    }
}