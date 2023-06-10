package com.example.bankingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Customer::class, Transaction::class], version = 1, exportSchema = false)
abstract class BankDatabase : RoomDatabase() {

    abstract fun bankDao(): BankDao

    companion object {
        @Volatile
        private var INSTANCE: BankDatabase? = null
        fun getInstance(context: Context, scope: CoroutineScope): BankDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BankDatabase::class.java,
                    "bank_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(BankDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class BankDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.bankDao())
                }
            }
        }
    }

}
    suspend fun populateDatabase(bankDao: BankDao) {
        val customers = mutableListOf<Customer>()
        customers.add(Customer("Ammar", "ammaryasser3016@gamil.com", 1000.00))
        customers.add(Customer("Yasser", "Yasser123@gamil.com", 1000.00))
        customers.add(Customer("Mohamed", "Mohamed123@gamil.com",1000.00))
        customers.add(Customer("Ahmed", "Ahmed123@gamil.com",1000.00))
        customers.add(Customer("Ibrahim", "Ibrahim123@gamil.com", 1000.00))
        customers.add(Customer("Hassan", "Hassan123@gamil.com",1000.00))
        customers.add(Customer("Mahmoud", "Mahmoud123@gamil.com",1000.00))
        customers.add(Customer("Abdo", "Abdo123@gamil.com",1000.00))
        customers.add(Customer("Youssef", "Youssef123@gamil.com",1000.00))
        customers.add(Customer("Eslam", "Eslam123@gamil.com",1000.00))
        customers.shuffle()
        customers.shuffle()
        bankDao.insertCustomers(customers)
    }

