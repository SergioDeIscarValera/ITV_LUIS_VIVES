

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class EmployeeRepositoryGenericTest {
    private lateinit var repository: EmployeeRepository

    abstract fun getRepository(): EmployeeRepository

    @BeforeEach
    open fun setUp() {
        repository = getRepository()
        repository.deleteAll()
        repository.saveAll(defaultValueEmployee())
    }

    @Test
    fun findAllTest() {
        assertAll(
            { assertEquals(defaultValueEmployee().size, repository.findAll().toList().size) },
    
        )
    }

    @Test
    fun findByIdTest(){
        val result = repository.findById(1)
        val resultFail = repository.findById(4)
        assertAll(
            { kotlin.test.assertEquals(defaultValueEmployee()[0], result) },
            { kotlin.test.assertEquals(null, resultFail) },
        )
    }

    @Test
    fun findByUserTest(){
        val result = repository.findByUser("soniagomez")
        val resultFail = repository.findByUser("ayaxprok")
        assertAll(
            { kotlin.test.assertEquals(defaultValueEmployee()[0], result) },
            { kotlin.test.assertEquals(null, resultFail) },
        )
    }

      @Test
    fun findByEmailTest(){
        val result = repository.findByEmail("soniagomez@gmail.com")
        val resultFail = repository.findByEmail("ayaxprok@gmail.com")
        assertAll(
            { kotlin.test.assertEquals(defaultValueEmployee()[0], result) },
            { kotlin.test.assertEquals(null, resultFail) },
        )

    @Test
    fun createTest(){
        val newEmpleado = defaultValueEmployee()[0].copy(id=4,nombre = "NEW")
        repository.save(newEmpleado)
        assertAll(
            { kotlin.test.assertEquals(5, repository.findAll().toList().size) },
            { kotlin.test.assertEquals(newEmpleado, repository.findById(4)) },
        )
    }

    @Test
    fun updateTest(){
        val newEmpleado = defaultValueEmployee()[1].copy(nombre = "NEW")
        repository.save(newEmpleado)
        assertAll(
            { kotlin.test.assertEquals(3, repository.findAll().toList().size) },
            { kotlin.test.assertEquals(newEmpleado, repository.findById(2)) },
        )
    }

    @Test
    fun deleteByIdTest(){
        repository.deleteById(1)
        assertAll(
            { kotlin.test.assertEquals(2, repository.findAll().toList().size) },
            { kotlin.test.assertEquals(null, repository.findById(1)) },
        )
    }

    @Test
    fun deleteByElement(){
        repository.delete(defaultValueEmployee()[0])
        assertAll(
            { kotlin.test.assertEquals(2, repository.findAll().toList().size) },
            { kotlin.test.assertEquals(null, repository.findById(1)) },
        )
    }


    @Test
    fun deleteAllTest(){
        repository.deleteAll()
        assertAll(
            { assertEquals(0, repository.findAll().toList().size) },
        )
    }

    @Test
    fun count(){
        assertEquals(3,repository.count())
    }
}