package com.example.itv_citas.di

import com.example.itv_citas.config.AppConfig
import com.example.itv_citas.repositories.appointment.*
import com.example.itv_citas.repositories.employee.*
import com.example.itv_citas.repositories.owner.*
import com.example.itv_citas.repositories.specialty.*
import com.example.itv_citas.repositories.station.*
import com.example.itv_citas.repositories.vehicle.*
import com.example.itv_citas.services.database.DataBaseManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind

val AppDiModule = module {
    singleOf(::AppConfig)
    singleOf(::DataBaseManager)
    singleOf(::AppointmentRepositoryDataBase){
        bind<AppointmentRepository>()
    }
    singleOf(::EmployeeRepositoryDataBase){
        bind<EmployeeRepository>()
    }
    singleOf(::OwnerRepositoryDataBase){
        bind<OwnerRepository>()
    }
    singleOf(::SpecialtyRepositoryDataBase){
        bind<SpecialtyRepository>()
    }
    singleOf(::StationRepositoryDataBase){
        bind<StationRepository>()
    }
    singleOf(::VehicleRepositoryDataBase){
        bind<VehicleRepository>()
    }
}