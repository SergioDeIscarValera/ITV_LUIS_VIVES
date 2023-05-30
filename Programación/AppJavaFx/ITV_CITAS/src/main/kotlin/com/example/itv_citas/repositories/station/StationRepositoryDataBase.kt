package com.example.itv_citas.repositories.station

import com.example.itv_citas.errors.StationError
import com.example.itv_citas.models.Station
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

class StationRepositoryDataBase: StationRepository, KoinComponent {
    private val dataBaseManager by inject<DataBaseManager>()

    override fun findAll(): Iterable<Station> {
        logger.debug { "StationRepositoryDataBase ->\tfindAll" }
        val stations = mutableListOf<Station>()
        val sql = """SELECT * FROM tEstacion"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                stations.add(
                    resultToStation(result)
                )
            }
        }
        return stations.toList()
    }

    override fun findById(id: Long): Result<Station, StationError> {
        logger.debug { "StationRepositoryDataBase ->\tfindById" }
        var station:Station? = null
        val sql = """SELECT * FROM tEstacion WHERE nId_Estacion = ?"""
        dataBaseManager.dataBase.prepareStatement(sql).use { stm ->
            stm.setLong(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                station = resultToStation(result)
            }
        }
        return if (station != null) Ok(station!!) else Err(StationError.StationNotFound)
    }

    private fun resultToStation(result: ResultSet): Station{
        return Station(
            result.getLong("nId_Estacion"),
            result.getString("cNombre"),
            result.getString("cCorreoElectronico"),
            result.getString("cDireccion"),
            result.getString("cTelefono")
        )
    }

    override fun existsById(id: Long): Boolean {
        logger.debug { "StationRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        logger.debug { "StationRepositoryDataBase ->\tcount" }
        return findAll().count()
    }
}