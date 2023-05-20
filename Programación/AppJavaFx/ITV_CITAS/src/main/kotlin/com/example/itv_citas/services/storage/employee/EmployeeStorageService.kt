package com.example.itv_citas.services.storage.employee

import com.example.itv_citas.errors.EmployeeError
import com.example.itv_citas.models.Employee
import com.example.itv_citas.services.storage.StorageService

interface EmployeeStorageService: StorageService<Employee, EmployeeError>