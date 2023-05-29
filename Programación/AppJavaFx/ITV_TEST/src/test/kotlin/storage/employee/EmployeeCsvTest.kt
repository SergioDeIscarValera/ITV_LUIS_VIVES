package storage.employee

import config.AppConfig
import default_values.DefaultValueEmployee.defaultValueEmployee
import errors.EmployeeError
import models.Employee
import services.storage.employee.EmployeeCsvStorage
import storage.StorageGenericTest
import java.io.File

class EmployeeCsvTest: StorageGenericTest<Employee, EmployeeError>() {

    private val appConfig = AppConfig

    override fun filePath() = "${appConfig.appData}${File.separator}employees.csv"

    override fun getStorage() = EmployeeCsvStorage

    override fun getListaDefault() = defaultValueEmployee()

    override fun getPath() = appConfig.appData
}