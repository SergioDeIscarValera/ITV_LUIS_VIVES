package com.example.itv_citas.repositories.employee

import com.example.itv_citas.errors.EmployeeError
import com.example.itv_citas.models.Employee
import com.example.itv_citas.repositories.SimpleCrud
import com.github.michaelbull.result.Result

interface EmployeeRepository: SimpleCrud<Employee, Long, EmployeeError>{
    fun saveAll(employees: List<Employee>): Result<List<Employee>, EmployeeError>
    fun findByUser(user: String): Result<Employee, EmployeeError>
    fun findByEmail(email:String):Result<Employee, EmployeeError>

}