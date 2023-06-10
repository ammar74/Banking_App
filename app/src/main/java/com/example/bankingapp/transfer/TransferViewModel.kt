package com.example.bankingapp.transfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.db.BankRepository
import com.example.bankingapp.db.Customer
import com.example.bankingapp.db.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.internal.InjectedFieldSignature
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {

    private val _completeTask = MutableLiveData<Boolean>()
    val completeTask = _completeTask
    val clientListLivedata = MutableLiveData< List<Customer?>>()

    init {
        getAllClients()
    }

    private fun getAllClients() {
        viewModelScope.launch {
            bankRepository.getCustomers().collect {
                clientListLivedata.value = it
            }
        }
    }

    fun insertTransaction(amount:Double,transferor:String,customer:Customer){
        val transaction = Transaction(System.currentTimeMillis(), transferor, customer.name, amount)
        viewModelScope.launch {
            val insertionResult = bankRepository.insertTransactionAndUpdate(transaction)
            if((insertionResult.getOrDefault(-1) > 0)){
                _completeTask.value = true
            }
        }
    }
}