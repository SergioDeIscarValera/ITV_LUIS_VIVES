package models

data class Report(
    val id: Long = count++,
    val favorable: Boolean,
    val braking: Double,
    val pollution: Double,
    val inside: Boolean,
    val lights: Boolean,
    val employee: Employee,
    val vehicle: Vehicle,
    val owner: Owner
){
    companion object {
        private var count = 0L
    }
}