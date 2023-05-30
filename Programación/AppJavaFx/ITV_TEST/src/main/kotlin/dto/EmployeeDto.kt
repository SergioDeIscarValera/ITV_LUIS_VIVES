package dto

data class EmployeeDto(
    val id: String,
    val name: String,
    val email: String,
    val userName: String,
    val phone: String,
    val password: String,
    val specialty: SpecialtyDto,
    val idStation: String,
    val idResponsable: String
)