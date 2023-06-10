package com.example.bankingapp.customers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.databinding.FragmentCustomerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomersFragment : Fragment() {
    private var binding: FragmentCustomerBinding? = null
    private lateinit var customerAdapter:CustomersAdapter
    private val customerViewModel: CustomersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerBinding.inflate(inflater, container, false)

        customerAdapter = CustomersAdapter(ItemClickListener { customer ->
            customerViewModel.onCustomerClicked(customer)
        })

        binding!!.customersRV.adapter = customerAdapter

        // Observing the client liveData and submit any change to the adapter.
        customerViewModel.customerListLivedata.observe(viewLifecycleOwner) { customers ->
            customerAdapter.submitList(customers)
        }
        customerViewModel.detailedCustomer.observe(viewLifecycleOwner) { customer ->
            customer?.let { it ->
                this.findNavController().navigate(
                    CustomersFragmentDirections.actionCustomersFragmentToDetailsFragment(it)
                )
                customerViewModel.doneNavigating()
            }
        }

        return binding?.root
    }
}