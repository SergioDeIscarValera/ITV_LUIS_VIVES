package repositories.appointment

import CurdTest

interface AppointmentRepositoryTest: CurdTest {
    fun findByIdEmployee()
    fun findByIdEmployeeDate()
    fun findByDate()
    fun findByDateAndCarNumber()
}