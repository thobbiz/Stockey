package com.example.project_umbrella.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_umbrella.model.Sale

@Database(entities = [Sale::class], version = 1, exportSchema = false)
abstract class SaleDatabase: RoomDatabase() {
    abstract fun saleDao(): SaleDao

    companion object{
        @Volatile
        private var Instance: SaleDatabase? = null

        fun getDatabase(context: Context): SaleDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SaleDatabase::class.java, "sales_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{Instance = it}
            }
        }
    }
}