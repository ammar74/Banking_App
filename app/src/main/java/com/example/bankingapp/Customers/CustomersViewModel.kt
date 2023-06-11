package com.example.bankingapp.customers

import androidx.lifecycle.*
import com.example.bankingapp.db.BankRepository
import com.example.bankingapp.db.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel  @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {
    private val _detailedCustomer = MutableLiveData<Customer?>()
    val detailedCustomer: LiveData<Customer?>
    get() = _detailedCustomer

    val customerListLivedata = MutableLiveData< List<Customer?>>()

    init {
        getAllCustomers()
    }

    // Save the value of the clicked client to be observed later.
    fun onCustomerClicked(customer: Customer) {
        _detailedCustomer.value = customer
    }

    // Event for navigating to detailedFragment successfully and make the value
    // of _detailedClient null.
    fun doneNavigating() {
        _detailedCustomer.value = null
    }
    private fun getAllCustomers() {
        viewModelScope.launch {
            bankRepository.getCustomers().collect{
                customerListLivedata.value=it
            }
        }

    }
}