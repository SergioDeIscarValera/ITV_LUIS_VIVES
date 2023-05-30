

abstract class {


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentRepsitoryTestDataBase: AppointmentRepositoryGenericTest() {
    private var repository = AppointmentRepositoryDataBase
    override fun getRepository() = repository

     @BeforeEach
    fun tearDown(){
        // Eliminar tablas
        executeSQLFile(AppConfig.appDBResetPath)
        // Crear tablas
        executeSQLFile(AppConfig.appDBInitPath)
        // Limpiar tablas (no es necesario)
        repository.deleteAll()
        // Insertar datos
        repository.saveAll(defaultValueEmployee())
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(DataBaseManager.dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }
}
}