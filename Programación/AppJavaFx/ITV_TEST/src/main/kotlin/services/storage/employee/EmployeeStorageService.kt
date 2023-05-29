package services.storage.employee

import services.storage.StorageService
import errors.EmployeeError
import models.Employee

interface EmployeeStorageService: StorageService<Employee, EmployeeError>