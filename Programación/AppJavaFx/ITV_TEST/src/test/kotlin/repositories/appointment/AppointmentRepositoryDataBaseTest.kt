package repositories.appointment

import di.AppDiModule
import errors.AppointmentError
import models.Appointment
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import config.AppConfig
import org.apache.ibatis.jdbc.ScriptRunner
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.core.context.startKoin
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader
import java.sql.DriverManager
import java.time.LocalDateTime
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentRepositoryDataBaseTest: AppointmentRepositoryTest {
    init {
        startKoin {
            printLogger()
            modules(AppDiModule)
        }
    }

    private val appConfig = AppConfig()
    val dataBase get() = DriverManager.getConnection(appConfig.appDBURL)

    private val repository = AppointmentRepositoryDataBase()

    private val appointmentsDefaultDataBase = listOf(
        Appointment(2, "2385KLP", LocalDateTime.parse("2023-06-01T08:49:04")),
        Appointment(3, "5421GVJ", LocalDateTime.parse("2023-06-06T14:45:51")),
        Appointment(4, "8796QWT", LocalDateTime.parse("2023-06-23T12:14:52")),
    )

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
    override fun findByIdEmployee() {
        val appointments = repository.findByIdEmployee(2)
        assertAll(
            { assertEquals(1, appointments.toList().size) },
            { assertEquals(appointmentsDefaultDataBase[0], appointments.toList()[0]) }
        )
    }
    @Test
    override fun findByDate() {
        val appointments = repository.findByDate(
            LocalDateTime.parse("2023-05-30T00:00:00"),
            LocalDateTime.parse("2023-06-07T00:00:00")
        )
        assertAll(
            { assertEquals(2, appointments.toList().size) },
            { assertEquals(appointmentsDefaultDataBase[0], appointments.toList()[0]) },
            { assertEquals(appointmentsDefaultDataBase[1], appointments.toList()[1]) },
        )
    }
    @Test
    override fun findByDateAndCarNumber() {
        val appointments = repository.findByDateAndCarNumber(
            "2385KLP",
            LocalDateTime.parse("2023-06-01T00:00:00"),
            LocalDateTime.parse("2023-06-02T00:00:00")
        )
        assertAll(
            { assertEquals(1, appointments.toList().size) },
            { assertEquals(appointmentsDefaultDataBase[0], appointments.toList()[0]) }
        )
    }
    @Test
    override fun findByIdEmployeeDate() {
        val appointments = repository.findByIdEmployee(
            2,
            LocalDateTime.parse("2023-06-01T00:00:00"),
            LocalDateTime.parse("2023-06-02T00:00:00")
        )
        assertAll(
            { assertEquals(1, appointments.toList().size) },
            { assertEquals(appointmentsDefaultDataBase[0], appointments.toList()[0]) }
        )
    }
    @Test
    override fun findAll() {
        val appointments = repository.findAll()
        println()
        assertAll(
            { assertEquals(appointmentsDefaultDataBase.size, appointments.toList().size) },
            { assertEquals(appointmentsDefaultDataBase[0], appointments.toList()[0]) },
            { assertEquals(appointmentsDefaultDataBase[1], appointments.toList()[1]) },
            { assertEquals(appointmentsDefaultDataBase[2], appointments.toList()[2]) }
        )
    }
    @Test
    override fun findById() {
        assertAll(
            { assertEquals(appointmentsDefaultDataBase[0], repository.findById("2385KLP").get()) },
            { assertTrue(repository.findById("9999ZZZ").getError() is AppointmentError.AppointmentNotFound) }
        )
    }
    @Test
    override fun create() {
        val appointment = Appointment(5, "6521RDS", LocalDateTime.parse("2023-06-23T12:14:52"))
        assertAll(
            { assertNotNull(repository.save(appointment).get()) },
            { assertEquals(appointment, repository.findById("6521RDS").get()) }
        )
    }
    @Test
    override fun update() {
        val copyAppointmet = appointmentsDefaultDataBase[2].copy(
            date = LocalDateTime.parse("2023-07-10T10:00:00")
        )
        repository.save(copyAppointmet)
        assertAll(
            { assertEquals(copyAppointmet, repository.findById("8796QWT").get()) },
            { assertEquals(3, repository.count()) }
        )
    }
    @Test
    override fun saveAll() {
        val appointmentsNew = listOf(
            Appointment(5, "6521RDS", LocalDateTime.parse("2023-06-23T12:14:52")),
            Appointment(1, "7843FBD", LocalDateTime.parse("2023-05-30T09:40:35")),
        )
        repository.saveAll(appointmentsNew)
        assertAll(
            { assertEquals(appointmentsNew[0], repository.findById("6521RDS").get()) },
            { assertEquals(appointmentsNew[1], repository.findById("7843FBD").get()) },
            { assertEquals(5, repository.count()) }
        )
    }
    @Test
    override fun deleteById() {
        repository.deleteById("2385KLP")
        assertAll(
            { assertTrue { repository.findById("2385KLP").getError() is AppointmentError.AppointmentNotFound } },
            { assertEquals(2, repository.count()) }
        )
    }
    @Test
    override fun delete() {
        repository.delete(appointmentsDefaultDataBase[0])
        assertAll(
            { assertTrue { repository.findById("2385KLP").getError() is AppointmentError.AppointmentNotFound }},
            { assertEquals(2, repository.count())}
        )
    }
    @Test
    override fun deleteAll() {
        repository.deleteAll()
        assertAll(
            { assertEquals(0, repository.findAll().toList().size) }
        )
    }
    @Test
    override fun existsById() {
        assertAll(
            { assertTrue { repository.existsById("2385KLP") } },
            { assertTrue { !repository.existsById("9999ZZZ") } }
        )
    }
    @Test
    override fun count() {
        assertAll(
            { assertEquals(3, repository.count()) }
        )
    }
}