import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.project_umbrella.data.AppDatabase
import com.example.project_umbrella.data.CustomerDao
import com.example.project_umbrella.model.Customer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CustomerDaoTest {
    private lateinit var customerDao: CustomerDao
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
        customerDao = appDatabase.customerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    private var customer1 = Customer(
        id = 1,
        name = "Islamyiat",
        phone = 8135518202,
        email = "odelolatojumi@gmail.com"
    )

    private var customer2 = Customer(
        id = 2,
        name = "supp",
        phone = 8034256866,
        email = "tojumiodelola@gmail.com"
    )

    private suspend fun addOneCustomerToDb() {
        customerDao.insertCustomer(customer1)
    }

    private suspend fun addTwoCustomersToDb() {
        customerDao.insertCustomer(customer1)
        customerDao.insertCustomer(customer2)
    }

    private suspend fun deleteCustomerFromDb() {
        customerDao.deleteCustomer(customer1.id)
    }

    private suspend fun updateCustomerInDb() {
        customerDao.updateCustomerPhone(customer1.id, 8023235173)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsCustomerInDB() = runBlocking {
        addOneCustomerToDb()
        val firstProduct = customerDao.getCustomer(customer1.id).first()
        assertEquals(firstProduct, customer1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsCustomersInDB() = runBlocking {
        addTwoCustomersToDb()
        val firstProduct = customerDao.getAllCustomers().first()
        assertEquals(firstProduct[0], customer1)
        assertEquals(firstProduct[1], customer2)
    }

    @Test
    @Throws(Exception::class)
    fun daoDelete_deletesCustomersInDB() = runBlocking {
        addOneCustomerToDb()
        deleteCustomerFromDb()
        val firstProduct = customerDao.getAllCustomers().first()
        assertTrue(firstProduct.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_updatesCustomersInDB() = runBlocking {
        addOneCustomerToDb()
        updateCustomerInDb()
        val firstProduct = customerDao.getCustomer(customer1.id).first()
        assertNotEquals(firstProduct.phone, customer1.phone)
    }
}