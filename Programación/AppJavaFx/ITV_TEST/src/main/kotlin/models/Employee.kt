package models

data class Employee(
    val id: Long,
    val name: String,
    val email: String,
    val userName: String,
    val phone: String,
    val password: String,
    val specialty: Specialty,
    val idEstacion: Long,
    val idResponsable: Long
)