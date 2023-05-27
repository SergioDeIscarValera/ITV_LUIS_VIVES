package repositories.owner

import SimpleCrudTest
import config.AppConfig
import default_values.DefaultValueOwner.defaultValueOwner
import default_values.DefaultValueVehicle.defaultValueVehicle
import di.AppDiModule
import models.Vehicle
import models.enums.TypeMotor
import models.enums.TypeVehicle
import org.apache.ibatis.jdbc.ScriptRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import repositories.appointment.AppointmentRepositoryDataBase
import repositories.owner.OwnerRepository
import repositories.vehicle.VehicleRepository
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OwnerRepositoryDataBaseTest: SimpleCrudTest {
    init {
        startKoin {
            printLogger()
            modules(AppDiModule)
        }
    }
    private val appConfig = AppConfig()
    val dataBase get() = DriverManager.getConnection(appConfig.appDBURL)

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var repository: OwnerRepositoryDataBase

    private val ownerDefaultValue = defaultValueOwner()

    @BeforeEach
    fun tearDown(){
        // Eliminar tablas
        executeSQLFile(appConfig.appDBResetPath)
        // Crear tablas
        executeSQLFile(appConfig.appDBInitPath)
        // Insertar datos
        executeSQLFile(appConfig.appDBInsertPath)
    }

    private fun executeSQLFile(sqlFile: String ){
        val sr = ScriptRunner(dataBase)
        val reader: Reader = BufferedReader(FileReader(sqlFile))
        sr.runScript(reader)
    }

    @Test
    override fun findAll() {
        whenever(vehicleRepository.findAll()).thenReturn(defaultValueVehicle())
        val owners = repository.findAll().toList()
        assertAll(
            { assertEquals(ownerDefaultValue.size, owners.size) },
            { assertEquals(ownerDefaultValue[0], owners[0]) }
        )
        verify(vehicleRepository, times(1)).findAll()
    }
    @Test
    override fun findById() {
        TODO("Not yet implemented")
    }
    @Test
    override fun existsById() {
        TODO("Not yet implemented")
    }
    @Test
    override fun count() {
        TODO("Not yet implemented")
    }
}