package com.example.itv_citas.repositories.employee

import com.example.itv_citas.errors.EmployeeError
import com.example.itv_citas.models.Employee
import com.example.itv_citas.repositories.SimpleCrud

interface EmployeeRepository: SimpleCrud<Employee, Long, EmployeeError>