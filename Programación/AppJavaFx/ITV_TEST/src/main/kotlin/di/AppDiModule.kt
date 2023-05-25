package di

import config.AppConfig
import services.database.DataBaseManager
import services.storage.appointment.AppointmentJsonStorage
import com.example.itv_citas.services.storage.appointment.AppointmentStorageService
import com.example.itv_citas.services.storage.employee.EmployeeCsvStorage
import com.example.itv_citas.services.storage.employee.EmployeeStorageService
import com.example.itv_citas.services.storage.report.ReportHtmlStorage
import com.example.itv_citas.services.storage.report.ReportJsonStorage
import com.example.itv_citas.services.storage.report.ReportStorageService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import repositories.appointment.AppointmentRepository
import repositories.appointment.AppointmentRepositoryDataBase
import repositories.employee.EmployeeRepository
import repositories.employee.EmployeeRepositoryDataBase
import repositories.owner.OwnerRepository
import repositories.owner.OwnerRepositoryDataBase
import repositories.specialty.SpecialtyRepository
import repositories.specialty.SpecialtyRepositoryDataBase
import repositories.station.StationRepository
import repositories.station.StationRepositoryDataBase
import repositories.vehicle.VehicleRepository
import repositories.vehicle.VehicleRepositoryDataBase

// Uso esta sintaxis para enlazar las implementaciones con las interfaces y así ser más escalable
val AppDiModule = module {
    singleOf(::AppConfig)

    //region Repositorios
    single(named("AppointmentBBDD")) { AppointmentRepositoryDataBase() } bind AppointmentRepository::class
    single(named("EmployeeBBDD")) { EmployeeRepositoryDataBase() } bind EmployeeRepository::class
    single(named("OwnerBBDD")) { OwnerRepositoryDataBase() } bind OwnerRepository::class
    single(named("SpecialtyBBDD")) { SpecialtyRepositoryDataBase() } bind SpecialtyRepository::class
    single(named("StationBBDD")) { StationRepositoryDataBase() } bind StationRepository::class
    single(named("VehicleBBDD")) { VehicleRepositoryDataBase() } bind VehicleRepository::class
    //endregion

    //region Servicios

    //region Database
    singleOf(::DataBaseManager)
    //endregion

    //region Storage
    single(named("AppointmentJSON")) { AppointmentJsonStorage() } bind AppointmentStorageService::class

    single(named("EmployeeCSV")) { EmployeeCsvStorage() } bind EmployeeStorageService::class

    single(named("ReportJSON")) { ReportJsonStorage() } bind ReportStorageService::class
    single(named("ReportHTML")) { ReportHtmlStorage() } bind ReportStorageService::class
    //endregion

    //endregion
}