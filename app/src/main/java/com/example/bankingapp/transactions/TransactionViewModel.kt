package com.example.bankingapp.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.db.BankRepository
import com.example.bankingapp.db.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {
    val transactionsListLiveData = MutableLiveData< List<Transaction?>>()

    init {
        getAllTransactions()
    }


    private fun getAllTransactions(){
    viewModelScope.launch {
        bankRepository.getTransactions().collect{//allTransactions
            transactionsListLiveData.value=it
        }
    }

}

}
