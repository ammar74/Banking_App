package com.example.bankingapp.customers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.databinding.ListItemBinding
import com.example.bankingapp.db.Customer

class CustomersAdapter (private val clickListener: ItemClickListener) :
    ListAdapter<Customer, CustomersAdapter.CustomerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class CustomerViewHolder (private val Binding: ListItemBinding) : RecyclerView.ViewHolder(Binding.root) {
        fun bind(item: Customer, clickListener: ItemClickListener) {
            Binding.customer=item
            Binding.clickListener=clickListener
            //وهنا بقوله هات الobject اللي اتعمل في ال binding
            Binding.executePendingBindings()
        }

    }
    }
    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.customerId == newItem.customerId
        }

        override fun areContentsTheSame(oldItem:Customer , newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }

   class ItemClickListener(val clickListener: (customer: Customer) -> Unit) {
        fun onClick(customer: Customer) = clickListener(customer)
    }
