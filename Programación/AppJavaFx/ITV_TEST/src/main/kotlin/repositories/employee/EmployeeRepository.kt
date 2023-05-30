package repositories.employee

import com.github.michaelbull.result.Result
import errors.EmployeeError
import models.Employee
import repositories.SimpleCrud

interface EmployeeRepository: SimpleCrud<Employee, Long, EmployeeError> {
    fun saveAll(employees: List<Employee>): Result<List<Employee>, EmployeeError>
    fun findByUser(user: String): Result<Employee, EmployeeError>
    fun findByEmail(email:String):Result<Employee, EmployeeError>
}