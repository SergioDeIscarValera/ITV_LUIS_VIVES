package repositories.appointment

import CurdTest

interface AppointmentRepositoryGenericTest: CurdTest {
   
    private lateinit var repository: AppointmentRepository
    abstract fun getRepository():AppointmentRepository


    @BeforeEach
    open fun setup(){
        repository = getRepository()
        repository.deleteAll()
        repository.saveAll(defaultValueAppointment())
    }

    @Test 
    fun findAllTest(){
        assertAll(
            {assertEquals(defaultValueAppointment().size,repository.findAll.toList().size)},
            {assertEquals(defaultValueAppointment()[0],repository.findAll.toList()[0])}
        )
    }

    @Test
    fun findByEmployee(){
        val empleadoBien = repository.findBy(1)
        val empleadoMal = repository.findBy(50)
        assertAll(
            {assertEquals(defaultValueAppointment()[0],empleadoBien)},
            {assertEquals(null,)}
        )
    }

    @Test 
    fun findByDate(){
        assertAll(
            {assertEquals(defaultValueAppointment()[0],findByDate("2023-06-01T08:00:00","2023-06-01T10:00:00"))},
            {assertEquals(null,findByDate("2024-06-01T08:00:00","2024-06-01T10:00:00"))}
        )
    }

    @Test 
    fun findByDateTest(){
        assertAll(
            {assertEquals(defaultValueAppointment()[0],repository.findById(1))}
            {assertEquals(defaultValueAppointment()[2],repository.findById(4))}
        )
    }


     @Test
    fun saveTest() {
        val newAppointment = Appointment(3, "1234FGR", LocalDateTime.now())
        val existingAppointment = defaultValueAppointment()[0]
        val result = repository.save(newAppointment)
        val resultExisting = repository.save(existingAppointment)

        assertAll(
            { assertTrue(result.isOk()) },
            { assertFalse(resultExisting.isOk()) }
        )
    }

    @Test
    fun saveAllTest() {
        val newAppointments = listOf(
            Appointment(5, "3421SDC", LocalDateTime.now()),
            Appointment(6, "9754BNK", LocalDateTime.now())
        )

        repository.saveAll(newAppointments)
        val allAppointments = repository.findAll()

        assertAll(
            { assertEquals(5, allAppointments.size) },
            { assertTrue(allAppointments.containsAll(newAppointments)) },
        )
    }

    @Test

    fun deleteAllTest(){
        repository.deleteAll()
        assertAll(
            assertEquals{0,repository.findAll().toList().size}
        )
    }

    @Test 
    fun deleteByIdTest(){
        repository.deleteById(2)
        assertAll(
            {assertEquals(2,repository.findAll().toList().size)}
            {assertEquals(null,repository.findById(2))}
        )
    }

    @Test
    fun deleteTest(){
        val appointment = defaultValueAppointment()[0]//Tiene Id 2
        repository.delete(appointment)
        assertAll(
            {assertEquals(2,repository.findAll().toList().size)}
            {assertEquals(null,repository.findById(2))}
        )
    }

    @Test countTest(){
        assertEquals(3,repository.count())
    }
}