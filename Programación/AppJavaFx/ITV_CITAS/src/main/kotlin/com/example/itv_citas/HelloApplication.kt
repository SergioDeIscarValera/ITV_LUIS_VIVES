package com.example.itv_citas

import com.example.itv_citas.di.AppDiModule
import com.example.itv_citas.models.Appointment
import com.example.itv_citas.repositories.appointment.AppointmentRepositoryDataBase
import com.example.javafxdemo.routes.RoutesManager
import com.github.michaelbull.result.mapBoth
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin
import java.time.LocalDateTime

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