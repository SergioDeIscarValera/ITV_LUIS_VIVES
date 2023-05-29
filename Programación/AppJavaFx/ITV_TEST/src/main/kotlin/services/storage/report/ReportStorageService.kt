package services.storage.report

import services.storage.StorageService
import errors.ReportError
import models.Report

interface ReportStorageService: StorageService<Report, ReportError>