package com.example.project_umbrella.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.project_umbrella.model.Customer
import com.example.project_umbrella.model.Entry
import com.example.project_umbrella.model.Order
import com.example.project_umbrella.model.OrderProduct
import com.example.project_umbrella.util.Converters

@Database(
    entities = [Product::class, Order::class, OrderProduct::class, Customer::class, Entry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun orderProductDao(): OrderProductDao
    abstract fun customerDao(): CustomerDao

    abstract fun entryDao(): EntryDao

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java,  "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}