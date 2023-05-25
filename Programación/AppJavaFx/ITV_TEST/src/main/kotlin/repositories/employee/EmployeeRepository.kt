package repositories.employee

import errors.EmployeeError
import models.Employee
import repositories.SimpleCrud
import com.github.michaelbull.result.Result

interface EmployeeRepository: SimpleCrud<Employee, Long, EmployeeError> {
    fun saveAll(employees: List<Employee>): Result<List<Employee>, EmployeeError>
    //val saveAll: (List<Employee>) -> Result<List<Employee>, EmployeeError>
}