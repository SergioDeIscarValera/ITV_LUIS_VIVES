module com.example.itv_citas {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    // Logger
    requires io.github.microutils.kotlinlogging;
    requires koin.logger.slf4j;
    requires org.slf4j;

    // Result
    requires kotlin.result.jvm;

    // Koin
    requires koin.core.jvm;

    // MyBatis
    requires org.mybatis;

    // BBDD
    requires java.sql;

    // Gson
    requires com.google.gson;

    // Jsoup
    requires org.jsoup;

    opens com.example.itv_citas to javafx.fxml;
    exports com.example.itv_citas;

    opens com.example.itv_citas.dto to com.google.gson;

    /*opens com.example.itv_citas.controllers to javafx.fxml;
    exports com.example.itv_citas.controllers;*/
}