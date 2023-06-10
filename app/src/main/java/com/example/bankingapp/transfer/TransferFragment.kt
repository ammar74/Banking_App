package com.example.bankingapp.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.customers.CustomersAdapter
import com.example.bankingapp.customers.ItemClickListener
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentTransferBinding
import com.example.bankingapp.db.Customer

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var binding: FragmentTransferBinding? = null
    private val transferViewModel: TransferViewModel by viewModels()

    private var amount: Double = 0.0
    private var transferor: String = ""
    private var transferorID: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transferor= TransferFragmentArgs.fromBundle(requireArguments()).transferor
        amount= TransferFragmentArgs.fromBundle(requireArguments()).amount.toDouble()
        transferorID= TransferFragmentArgs.fromBundle(requireArguments()).transferorId

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val customerAdapter = CustomersAdapter(ItemClickListener{
            insertTransaction(it)
        })
        binding!!.recReceiversList.adapter = customerAdapter

        binding?.recReceiversList?.layoutManager = LinearLayoutManager(requireContext())
        transferViewModel.clientListLivedata.observe(viewLifecycleOwner) { customers ->
            val list = customers.toMutableList()
            list.removeAt(transferorID - 1)
            customerAdapter.submitList(list)
        }

        transferViewModel.completeTask.observe(viewLifecycleOwner) { isTaskCompleted ->
            if (isTaskCompleted == true) {
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_transferFragment_to_customersFragment)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding!!.root    }

    private fun insertTransaction(customer: Customer) {
        transferViewModel.insertTransaction(amount,transferor,customer)
    }
}
