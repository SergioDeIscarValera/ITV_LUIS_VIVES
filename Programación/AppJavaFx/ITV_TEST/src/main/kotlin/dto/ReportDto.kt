package dto


data class ReportDto(
    val id: String = "0",
    val favorable: String,
    val braking: String,
    val pollution: String,
    val inside: String,
    val lights: String,
    val employee: EmployeeDto,
    val vehicle: VehicleDto,
    val owner: OwnerDto
)