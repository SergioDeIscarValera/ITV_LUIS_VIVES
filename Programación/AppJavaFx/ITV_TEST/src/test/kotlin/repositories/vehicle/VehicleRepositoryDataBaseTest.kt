package repositories.vehicle

import SimpleCrudTest
import config.AppConfig
import default_values.DefaultValueVehicle.defaultValueVehicle
import di.AppDiModule
import models.Vehicle
import models.enums.TypeMotor
import models.enums.TypeVehicle
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.core.context.startKoin
import repositories.appointment.AppointmentRepositoryDataBase
import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VehicleRepositoryDataBaseTest: SimpleCrudTest {
    init {
        startKoin {
            printLogger()
            modules(AppDiModule)
        }
    }
    private val appConfig = AppConfig()
    val dataBase get() = DriverManager.getConnection(appConfig.appDBURL)


    private val repository = AppointmentRepositoryDataBase()

    private val vehicleDefaultDataBase = defaultValueVehicle()


    @Test
    override fun findAll() {

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