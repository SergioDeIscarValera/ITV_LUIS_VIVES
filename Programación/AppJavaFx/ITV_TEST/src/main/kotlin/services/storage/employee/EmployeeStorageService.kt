package com.example.itv_citas.services.storage.employee

import errors.EmployeeError
import models.Employee
import com.example.itv_citas.services.storage.StorageService

interface EmployeeStorageService: StorageService<Employee, EmployeeError>