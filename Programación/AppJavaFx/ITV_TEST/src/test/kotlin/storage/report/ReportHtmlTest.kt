package storage.report

import com.example.itv_citas.services.storage.report.ReportHtmlStorage
import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.toResultOr
import config.AppConfig
import default_values.DefaultValueEmployee.defaultValueEmployee
import default_values.DefaultValueOwner.defaultValueOwner
import default_values.DefaultValueReport.defaultValueReport
import errors.OwnerError
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import repositories.employee.EmployeeRepositoryDataBase
import repositories.owner.OwnerRepositoryDataBase
import repositories.vehicle.VehicleRepositoryDataBase
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension::class)
class ReportHtmlTest {
    @Mock
    lateinit var ownerRepository: OwnerRepositoryDataBase

    @Mock
    lateinit var employeeRepository: EmployeeRepositoryDataBase

    @Mock
    lateinit var vehicleRepository: VehicleRepositoryDataBase

    @InjectMocks
    lateinit var storage: ReportHtmlStorage

    private val appConfig = AppConfig()
    private val filePath = "${appConfig.appData}${File.separator}reports.html"
    private val defaultValue = defaultValueReport()
    private val dataPath = appConfig.appData

    @AfterAll
    fun cleanFiles() {
        val file = File(filePath)
    }

    @Test
    fun saveAllTest() {
        storage.saveAll(defaultValue, dataPath)
        val file = File(dataPath)
        assertNotNull(file.validate(FileAction.WRITE).get())
    }

    @Test
    fun loadAllTest() {
        whenever(ownerRepository.findById("54875241R")).thenReturn(Ok(defaultValueOwner()[0]))
        whenever(ownerRepository.findById("51487524T")).thenReturn(Ok(defaultValueOwner()[1]))
        whenever(employeeRepository.findById(1)).thenReturn(Ok(defaultValueEmployee()[0]))
        whenever(employeeRepository.findById(2)).thenReturn(Ok(defaultValueEmployee()[1]))
        whenever(vehicleRepository.findById("5421GVJ")).thenReturn(Ok(defaultValueOwner()[0].vehicles[0]))
        whenever(vehicleRepository.findById("7843FBD")).thenReturn(Ok(defaultValueOwner()[1].vehicles[0]))
        val file = File(filePath)
        if (!file.exists()) storage.saveAll(defaultValue, filePath)
        assertNotNull(file.validate(FileAction.READ).get())
        assertEquals(defaultValue.size, storage.loadAll(filePath).get()?.size)
        verify(ownerRepository).findById("54875241R")
        verify(ownerRepository).findById("51487524T")
        verify(employeeRepository).findById(1)
        verify(employeeRepository).findById(2)
        verify(vehicleRepository).findById("5421GVJ")
        verify(vehicleRepository).findById("7843FBD")
    }
}