package storage

import com.example.itv_citas.services.storage.StorageService
import com.example.itv_citas.validators.FileAction
import com.example.itv_citas.validators.validate
import com.github.michaelbull.result.get
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class StorageGenericTest<T, ERR> {
    abstract fun filePath(): String
    abstract fun getStorage(): StorageService<T, ERR>
    abstract fun getListaDefault(): List<T>
    abstract fun getPath(): String

    @AfterAll
    fun cleanFiles(){
        val file = File(filePath())
//        if (file.exists()) file.delete()
    }

    @Test
    fun saveAllTest(){
        getStorage().saveAll(getListaDefault(), getPath())
        // Comprobar que el fichero existe
        val file = File(filePath())
        assertNotNull(file.validate(FileAction.WRITE).get())
    }

    @Test
    fun loadAllTest(){
        val file = File(filePath())
        if (!file.exists()) getStorage().saveAll(getListaDefault(), filePath())
        assertNotNull(file.validate(FileAction.READ).get())
        assertEquals(getListaDefault().size, getStorage().loadAll(filePath()).get()?.size)
    }
}