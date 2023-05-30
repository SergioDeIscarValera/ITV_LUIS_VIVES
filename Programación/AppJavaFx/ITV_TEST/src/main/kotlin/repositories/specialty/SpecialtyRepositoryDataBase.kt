package repositories.specialty

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import errors.SpecialtyError
import models.Specialty
import mu.KotlinLogging
import services.database.DataBaseManager
import services.database.DataBaseManager.dataBase
import java.sql.ResultSet

private val logger = KotlinLogging.logger {}

class SpecialtyRepositoryDataBase: SpecialtyRepository {

    override fun findAll(): Iterable<Specialty> {
        logger.debug { "SpecialtyRepositoryDataBase ->\tfindAll" }
        val specialties = mutableListOf<Specialty>()
        val sql = """SELECT * FROM tEspecialidad"""
        dataBase.prepareStatement(sql).use { stm ->
            val result = stm.executeQuery()
            while (result.next()){
                specialties.add(
                    resultToSpecialty(result)
                )
            }
        }
        return specialties.toList()
    }

    override fun findById(id: String): Result<Specialty, SpecialtyError> {
        logger.debug { "SpecialtyRepositoryDataBase ->\tfindById" }
        var specialty:Specialty? = null
        val sql = """SELECT * FROM tEspecialidad WHERE cNombre = ?"""
        dataBase.prepareStatement(sql).use { stm ->
            stm.setString(1, id)
            val result = stm.executeQuery()
            while (result.next()){
                specialty = resultToSpecialty(result)
            }
        }
        return if (specialty != null) Ok(specialty!!) else Err(SpecialtyError.SpecialtyNotFound)
    }

    private fun resultToSpecialty(result: ResultSet) = Specialty(
        result.getString("cNombre"),
        result.getInt("nSalario")
    )

    override fun existsById(id: String): Boolean {
        logger.debug { "SpecialtyRepositoryDataBase ->\texistsById" }
        return findById(id).get() != null
    }

    override fun count(): Int {
        logger.debug { "SpecialtyRepositoryDataBase ->\tcount" }
        return findAll().count()
    }
}