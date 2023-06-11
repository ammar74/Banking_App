package com.example.bankingapp.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.databinding.FragmentTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment() {
    private var binding: FragmentTransactionsBinding? = null
    private lateinit var transactionsAdapter: TransactionsAdapter
    private val transactionViewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)

        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionsAdapter = TransactionsAdapter()
        binding?.TransactionsList?.adapter = transactionsAdapter
        binding?.TransactionsList?.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel.transactionsListLiveData.observe(viewLifecycleOwner) { transactions ->
            transactions.let { transactionsAdapter.submitList(it) }
        }
    }
}