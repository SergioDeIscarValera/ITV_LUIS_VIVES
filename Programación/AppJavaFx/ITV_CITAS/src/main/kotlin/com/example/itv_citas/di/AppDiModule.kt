package com.example.itv_citas.di

import com.example.itv_citas.config.AppConfig
import com.example.itv_citas.repositories.appointment.*
import com.example.itv_citas.repositories.employee.*
import com.example.itv_citas.repositories.owner.*
import com.example.itv_citas.repositories.specialty.*
import com.example.itv_citas.repositories.station.*
import com.example.itv_citas.repositories.vehicle.*
import com.example.itv_citas.services.database.DataBaseManager
import com.example.itv_citas.services.storage.appointment.AppointmentJsonStorage
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