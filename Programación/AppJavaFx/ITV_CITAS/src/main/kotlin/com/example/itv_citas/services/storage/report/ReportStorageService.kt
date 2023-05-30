package com.example.itv_citas.services.storage.report

import com.example.itv_citas.errors.ReportError
import com.example.itv_citas.models.Report
import com.example.itv_citas.services.storage.StorageService

interface ReportStorageService: StorageService<Report, ReportError>