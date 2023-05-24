package com.example.javafxdemo.routes

enum class Views(val fxml: String, val size: Pair<Double, Double>) {
    LOGIN("views/principal-view-citas.fxml", Pair(431.0, 394.0)),
    HOME("views/citas-view.fxml", Pair(911.2, 586.0)),
    ACERCA_DE("views/acerca-de-view.fxml", Pair(418.0, 234.0)),
    FORM_CITAS("views/gestion-cita-view.fxml", Pair(318.0, 460.0)),
    FORM_INFORMES("views/informe-view.fxml", Pair(363.0, 512.0)),
}