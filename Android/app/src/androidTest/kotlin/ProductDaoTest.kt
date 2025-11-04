import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_umbrella.data.AppDatabase
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.data.ProductDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
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
        name = "Coaster",
        costPrice = 1200.0,
        sellingPrice = 1500.0,
        quantity = 120,
        unit = "packs",
        description = "nothing"
    )

    private var product2 = Product(
        id = 2,
        owner = "me",
        name = "ParleG",
        costPrice = 1200.0,
        sellingPrice = 1500.0,
        quantity = 120,
        unit = "packs",
        description = "nothing"
    )

    private suspend fun addOneProductToDb() {
        productDao.insertProduct(product1)
    }

    private suspend fun addTwoProductsToDb() {
        productDao.insertProduct(product1)
        productDao.insertProduct(product2)
    }

    private suspend fun deleteProductsFromDb() {
        productDao.deleteProduct(product1.id)
    }

    private suspend fun addProductQuantityInDb() {
        productDao.addQuantity(product1.id, 50)
    }

    private suspend fun updateSellingPriceInDb() {
        productDao.updateSellingPrice(product1.id, 1500.0)
    }

    private suspend fun updateCostPriceInDb() {
        productDao.updateCostPrice(product1.id, 1500.0)
    }


    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsProductIntoDB() = runBlocking {
        addOneProductToDb()
        val firstProduct = productDao.getAllProducts().first()
        assertEquals(firstProduct[0], product1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDelete_deleteProductFromDB() = runBlocking {
        addOneProductToDb()
        deleteProductsFromDb()
        val allProducts = productDao.getAllProducts().first()
        assertTrue(allProducts.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllProducts_returnsAllProductsFromDB() = runBlocking {
        addTwoProductsToDb()
        val allItems = productDao.getAllProducts().first()
        assertEquals(allItems[0], product1)
        assertEquals(allItems[1], product2)
    }

    @Test
    @Throws(Exception::class)
    fun daoAddQuantity_returnUpdateProductsFromDB() = runBlocking {
        addOneProductToDb()
        addProductQuantityInDb()
        val firstProduct = productDao.getProduct(product1.id).first()
        assertEquals(firstProduct.quantity - product1.quantity, 50)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateSellingPrice_returnUpdateProductFromDB() = runBlocking {
        addOneProductToDb()
        updateSellingPriceInDb()
        val firstProduct = productDao.getProduct(product1.id).first()
        assertEquals(firstProduct.sellingPrice, 1500.00, 1.0)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateCostPrice_returnUpdateProductFromDB() = runBlocking {
        addOneProductToDb()
        updateCostPriceInDb()
        val firstProduct = productDao.getProduct(product1.id).first()
        assertEquals(firstProduct.costPrice, 1500.00, 1.0)
    }
}


