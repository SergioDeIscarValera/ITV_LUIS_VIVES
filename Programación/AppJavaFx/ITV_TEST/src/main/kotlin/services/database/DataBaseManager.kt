package services.database

import config.AppConfig
import mu.KotlinLogging
import org.apache.ibatis.jdbc.ScriptRunner
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.sql.DriverManager

private val logger = KotlinLogging.logger {  }

class DataBaseManager: KoinComponent{
    private val appConfig by inject<AppConfig>()
    val dataBase get() = DriverManager.getConnection(appConfig.appDBURL)

    init {
        logger.debug { "DataBaseManager ->\tinit" }

        if (appConfig.appDBReset){
            logger.debug { "DataBaseManager ->\tinit ->\treset" }
            executeSQLFile(appConfig.appDBResetPath)
        }

        executeSQLFile(appConfig.appDBInitPath)

        if (appConfig.appDBInsert){
            logger.debug { "DataBaseManager ->\tinit ->\tinsert" }
            executeSQLFile(appConfig.appDBInsertPath)
        }
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }
}