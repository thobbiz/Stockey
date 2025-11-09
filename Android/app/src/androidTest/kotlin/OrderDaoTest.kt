import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.project_umbrella.data.AppDatabase
import com.example.project_umbrella.data.OrderDao
import com.example.project_umbrella.data.Product
import com.example.project_umbrella.data.ProductDao
import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderProduct
import com.example.project_umbrella.model.OrderStatus
import com.example.project_umbrella.model.PaymentMethod
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class OrderDaoTest {
    private lateinit var orderDao: OrderDao

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
        orderDao = appDatabase.orderDao()
        productDao = appDatabase.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    private var order1 = Order(
        id = 1,
        customerId = null,
        comment = "order1"
    )

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

    private var product2 = Product(
        id = 2,
        owner = "me",
        name = "Coaster",
        costPrice = 1200.0,
        sellingPrice = 1500.0,
        quantity = 120,
        unit = "packs",
        description = "nothing"
    )

    private var orderProduct1 = OrderProduct(
        orderId = order1.id,
        productId = product1.id,
        price = product1.sellingPrice,
        quantity = 2
    )

    private var orderProduct2 = OrderProduct(
        orderId = order1.id,
        productId = product2.id,
        price = product2.sellingPrice,
        quantity = 2
    )

    var orderProductList = listOf(orderProduct1, orderProduct2)


    private suspend fun addTwoProductsToDb() {
        productDao.insertProduct(product1)
        productDao.insertProduct(product2)
    }

    private suspend fun addOrderToDb() {
        orderDao.insertOrder(order1)
        orderDao.insertOrderProducts(orderProductList)
    }

    private suspend fun updateOrderTotalAmountToDb(totalAmount: Double) {
        orderDao.updateOrderTotal(order1.id, totalAmount)
    }

    private suspend fun updateOrderStatus(orderStatus: OrderStatus) {
        orderDao.updateOrderStatus(order1.id, orderStatus.orderStatusName)
    }

    private suspend fun updateOrderPaymentMethod(paymentMethod: PaymentMethod) {
        orderDao.updatePaymentMethod(order1.id, paymentMethod.paymentMethodName)
    }

    @Test
    @Throws(Exception::class)
    fun daoGet_getOrderFromDB() = runBlocking {
        addTwoProductsToDb()
        addOrderToDb()
        val order = orderDao.getOrderWithProducts(order1.id).first()
        assertNotNull(order)
        assertEquals(orderProduct1, order!!.orderProducts[0])
        assertEquals(order1, order.order)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdate_updateOrderTotalAmountFromDB() = runBlocking {
        addTwoProductsToDb()
        addOrderToDb()
        val totalAmount = orderDao.calculateOrderTotal(order1.id).first()
        assertNotNull(totalAmount)
        updateOrderTotalAmountToDb(totalAmount)
        val order = orderDao.getOrderWithProducts(order1.id).first()
        assertNotNull(order)
        assertNotEquals(order1.totalAmount, order?.order?.totalAmount)
        print(totalAmount)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdate_updateOrderStatusMethodFromDB() = runBlocking {
        addTwoProductsToDb()
        addOrderToDb()
        updateOrderStatus(OrderStatus.OrderStatusCompleted)
        val order1WithProducts = orderDao.getOrderWithProducts(order1.id).first()
        assertNotNull(order1WithProducts)
        assertNotEquals(order1WithProducts?.order, order1)
        assertEquals(OrderStatus.OrderStatusCompleted.orderStatusName, order1WithProducts?.order?.orderStatus)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdate_updateOrderPaymentMethodFromDB() = runBlocking {
        addTwoProductsToDb()
        addOrderToDb()
        updateOrderPaymentMethod(paymentMethod = PaymentMethod.PaymentMethodBankTransfer)
        val order1WithProducts = orderDao.getOrderWithProducts(order1.id).first()
        assertNotNull(order1WithProducts)
        assertNotEquals(order1WithProducts?.order, order1)
        assertEquals(PaymentMethod.PaymentMethodBankTransfer.paymentMethodName, order1WithProducts?.order?.paymentMethod)
    }
}

