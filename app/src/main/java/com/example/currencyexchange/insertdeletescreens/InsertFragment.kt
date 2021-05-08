package com.example.currencyexchange.insertdeletescreens

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.currencyexchange.R
import com.example.currencyexchange.database.Spending
import com.example.currencyexchange.database.SpendingViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_insert.view.*
import kotlinx.android.synthetic.main.fragment_name.view.buttonDone
import kotlinx.android.synthetic.main.fragment_name.view.buttonSkip

class InsertFragment : Fragment() {

    lateinit var v: View

    private lateinit var mSpendingViewModel: SpendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_insert, container, false)

        mSpendingViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)

        v.buttonDone.setOnClickListener {
            insertDataToDatabase()
        }

        v.buttonSkip.setOnClickListener{
            findNavController().navigate(R.id.action_insertFragment_to_homeFragment)
        }

        return v
    }

    private fun insertDataToDatabase() {
        val descText: TextInputEditText = v.editSpendingExplanation
        val costText: TextInputEditText = v.editSpendingAmount
        val spendingType: RadioGroup = v.typeRadioGroup2
        val currencyType: RadioGroup = v.typeRadioGroup3

        if(inputCheck(descText.text.toString(), costText.text.toString(), spendingType.checkedRadioButtonId.toString(), currencyType.checkedRadioButtonId.toString())) {
            var type: Int = 0
            when (spendingType.checkedRadioButtonId) {
                R.id.bill -> type = 1
                R.id.rent -> type = 2
                R.id.shopping -> type = 0
            }
            var currency: String = "USD"
            when (currencyType.checkedRadioButtonId) {
                R.id.turkishlira -> currency = "TRY"
                R.id.americandollar -> currency = "USD"
                R.id.europeaneuro -> currency = "EUR"
                R.id.britishpound -> currency = "GBP"
            }

            val spending = Spending(descText.text.toString(),costText.text.toString().toDouble(), type, currency)
            mSpendingViewModel.addSpending(spending)
            Toast.makeText(requireContext(), "Succesfully Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_insertFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(description: String, cost: String, type: String, currency: String): Boolean {
        return !(TextUtils.isEmpty(description) && TextUtils.isEmpty(cost) && TextUtils.isEmpty(type) && TextUtils.isEmpty(currency))
    }
}