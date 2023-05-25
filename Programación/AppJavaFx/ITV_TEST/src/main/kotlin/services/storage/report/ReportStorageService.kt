package com.example.itv_citas.services.storage.report

import errors.ReportError
import models.Report
import com.example.itv_citas.services.storage.StorageService

interface ReportStorageService: StorageService<Report, ReportError>