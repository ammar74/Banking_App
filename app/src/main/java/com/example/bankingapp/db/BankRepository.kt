package com.example.bankingapp.db

import kotlinx.coroutines.flow.Flow

class BankRepository(private val bankDao: BankDao) {

    fun getCustomers(): Flow<List<Customer>> {
        return bankDao.getCustomers()
    }

    val allTransactions: Flow<List<Transaction>> = bankDao.getTransactions()

    suspend fun insertTransactionAndUpdate(transaction: Transaction): Result<Int>{
        return try {
            Result.success(bankDao.insertAndUpdateTransaction(transaction))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}