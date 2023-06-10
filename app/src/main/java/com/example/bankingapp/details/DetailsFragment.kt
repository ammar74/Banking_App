package com.example.bankingapp.details

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentDetailsBinding
import com.example.bankingapp.db.Customer

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private lateinit var currentCustomer: Customer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentCustomer = DetailsFragmentArgs.fromBundle(requireArguments()).customer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.customer= currentCustomer

        binding?.doTransactionBTN?.setOnClickListener {
            showDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding!!.root    }

    private fun showDialog() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val editAmount = EditText(requireContext())
        editAmount.inputType = InputType.TYPE_CLASS_NUMBER
        editAmount.setRawInputType(Configuration.KEYBOARD_12KEY)
        editAmount.hint = "Amount"
        editAmount.setSingleLine()
        alert.setTitle(getString(R.string.EnterAmount))
        alert.setView(editAmount)

        alert.setPositiveButton("OK") { dialog, which -> }
        alert.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        val dialog = alert.create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val amountString: String = editAmount.text.toString().trim()
                    if (amountString.isEmpty()) {
                        editAmount.error = getString(R.string.warning)
                        return
                    } else {
                        val amount: Double = amountString.toDouble()
                        if (amount > currentCustomer.balance){
                            editAmount.error = getString(R.string.balance_warning)
                            return
                        }
                        findNavController().navigate(DetailsFragmentDirections
                            .actionDetailsFragmentToTransferFragment
                                (currentCustomer.name,currentCustomer.customerId, amount.toFloat()))
                        dialog.dismiss()


                    }
                }
             })
    }

}