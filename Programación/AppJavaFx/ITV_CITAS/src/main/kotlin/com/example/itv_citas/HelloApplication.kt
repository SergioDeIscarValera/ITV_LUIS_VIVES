package com.example.itv_citas

import com.example.itv_citas.di.AppDiModule
import com.example.itv_citas.repositories.employee.EmployeeRepositoryDataBase
import com.example.javafxdemo.routes.RoutesManager
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        startKoin {
            printLogger()
            modules(AppDiModule)
        }
        RoutesManager.setStage(stage)
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}