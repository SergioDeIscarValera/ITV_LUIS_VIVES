plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Result
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.17")

    // Log
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    //MyBatis
    implementation("org.mybatis:mybatis:3.5.11")

    //MySql
    implementation("mysql:mysql-connector-java:8.0.28")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Jsoup
    implementation("org.jsoup:jsoup:1.15.3")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.3.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}