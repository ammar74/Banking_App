package com.example.bankingapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDao {

    @Query("SELECT * FROM customer_table")
    fun getCustomers(): Flow<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomers(words : List<Customer>)

    @Query("DELETE FROM customer_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query("Update customer_table set balance = balance - :amount where name = :transferor")
    suspend fun decreaseMoney(amount : Double, transferor: String):Int

    @Query("Update customer_table set balance = balance + :amount where name = :transferee")
    suspend fun increaseMoney(amount : Double, transferee: String): Int

    @androidx.room.Transaction
    suspend fun insertAndUpdateTransaction(transaction: Transaction) : Int{
        val idOfInsertedItem = insertTransaction(transaction)
        if (idOfInsertedItem > 0){
            val decreaseResult = decreaseMoney(transaction.amount, transaction.transferor)
            val increaseResult = increaseMoney(transaction.amount, transaction.transferee)
            if((decreaseResult > 0) && (increaseResult> 0)){
                return 1
            }
        }
        return -1
    }

    @Query("SELECT * FROM transaction_table")
    fun getTransactions(): Flow<List<Transaction>>
}
