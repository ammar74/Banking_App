package ccom.example.bankingapp.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.db.BankRepository
import com.example.bankingapp.db.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel
@Inject constructor(private val bankRepository: BankRepository) : ViewModel() {
    val allTransactions = MutableLiveData< List<Transaction?>>()

    init {
        getTransactions()
    }


    private fun getTransactions(){
    viewModelScope.launch {
        bankRepository.allTransactions.collect{
            allTransactions.value=it
        }
    }

}

}
