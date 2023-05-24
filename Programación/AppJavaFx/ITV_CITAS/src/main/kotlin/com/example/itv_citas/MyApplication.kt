package com.example.itv_citas

import com.example.itv_citas.di.AppDiModule
import com.example.itv_citas.route.RoutesManager
import com.example.javafxdemo.routes.Views
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun start(stage: Stage) {
        startKoin {
            printLogger()
            modules(AppDiModule)
        }
        RoutesManager.apply {
            app = this@MyApplication
        }
        RoutesManager.setStage(stage)
        RoutesManager.addScreens(
            Views.values()
        )
        /*RoutesManager.changeScene(
            Views.LOGIN,
            "Login"
        )*/
        RoutesManager.changeScene(
            Views.HOME,
            "Home"
        )
    }
}

fun main() {
    Application.launch(MyApplication::class.java)
}