import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_umbrella.data.AppDatabase
import com.example.project_umbrella.data.EntryDao
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.data.ProductDao
import com.example.project_umbrella.model.Entry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {
    private lateinit var entryDao: EntryDao
    private lateinit var productDao: ProductDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        entryDao = appDatabase.entryDao()
        productDao = appDatabase.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    private var product1 = Product(
        id = 1,
        owner = "me",
        name = "ParleG",
        costPrice = 1200.0,
        sellingPrice = 1500.0,
        quantity = 120,
        unit = "packs",
        description = "nothing"
    )


    private var entry1 = Entry(
        id = 1,
        productId = product1.id,
        quantity = 40,
    )

    private suspend fun addProductsToDb() {
        productDao.insertProduct(product1)
    }

    private suspend fun addEntryToDb() {
        entryDao.insertEntry(entry1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGet_getEntryFromDB() = runBlocking {
        addProductsToDb()
        addEntryToDb()

        val entry = entryDao.getEntry(entry1.id).first()
        assertNotNull(entry)
        assertEquals(entry1, entry)
    }

    @Test
    @Throws(Exception::class)
    fun daoGet_getAllEntriesFromDB() = runBlocking {
        addProductsToDb()
        addEntryToDb()

        val entry = entryDao.getAllEntries().first()
        assertNotNull(entry)
        assertEquals(entry1, entry[0])
    }
}