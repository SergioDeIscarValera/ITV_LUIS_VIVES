package services.database

import config.AppConfig
import mu.KotlinLogging
import org.apache.ibatis.jdbc.ScriptRunner
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.sql.DriverManager

private val logger = KotlinLogging.logger {  }

object DataBaseManager{
    val dataBase get() = DriverManager.getConnection(AppConfig.appDBURL)

    init {
        logger.debug { "DataBaseManager ->\tinit" }

        if (AppConfig.appDBReset){
            logger.debug { "DataBaseManager ->\tinit ->\treset" }
            executeSQLFile(AppConfig.appDBResetPath)
        }

        executeSQLFile(AppConfig.appDBInitPath)

        if (AppConfig.appDBInsert){
            logger.debug { "DataBaseManager ->\tinit ->\tinsert" }
            executeSQLFile(AppConfig.appDBInsertPath)
        }
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }
}