package storage.report

import com.example.itv_citas.services.storage.report.ReportJsonStorage
import config.AppConfig
import default_values.DefaultValueReport.defaultValueReport
import errors.ReportError
import models.Report
import storage.StorageGenericTest
import java.io.File

class ReportJsonTest: StorageGenericTest<Report, ReportError>() {

    private val appConfig = AppConfig()

    override fun filePath() = "${appConfig.appData}${File.separator}reports.json"

    override fun getStorage() = ReportJsonStorage()

    override fun getListaDefault() = defaultValueReport()

    override fun getPath() = appConfig.appData
}