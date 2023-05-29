package repositories.station

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import errors.StationError
import models.Station
import mu.KotlinLogging
import services.database.DataBaseManager
import services.database.DataBaseManager.dataBase
import java.sql.ResultSet

private val logger = KotlinLogging.logger {}

class StationRepositoryDataBase: StationRepository {

    override fun findAll(): Iterable<Station> {
        logger.debug { "StationRepositoryDataBase ->\tfindAll" }
        val stations = mutableListOf<Station>()
        val sql = """SELECT * FROM tEstacion"""
        dataBase.prepareStatement(sql).use { stm ->
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
        dataBase.prepareStatement(sql).use { stm ->
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