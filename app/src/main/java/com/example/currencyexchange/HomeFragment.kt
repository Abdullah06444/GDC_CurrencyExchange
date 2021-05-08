package com.example.currencyexchange

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchange.apiconnection.Connection
import com.example.currencyexchange.apiconnection.CurrencyApi
import com.example.currencyexchange.apiconnection.CurrencyConverter
import com.example.currencyexchange.database.SpendingListAdapter
import com.example.currencyexchange.database.SpendingViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(),View.OnClickListener {

    lateinit var v: View
    val gender = "GENDER"
    val name = "NAME"
    private lateinit var spendingViewModel: SpendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        isOnline()
        addName()
        recyclerView("TRY") // addSpending & deleteSpending

        v.buttonTRY.setOnClickListener(this)
        v.buttonUSD.setOnClickListener(this)
        v.buttonEUR.setOnClickListener(this)
        v.buttonGBP.setOnClickListener(this)
        return v
    }

    private fun isOnline(){

        Connection(requireContext()).observe(requireActivity(), {
                isconnected ->
            if (isconnected){
                CurrencyApi(requireContext()).getData()
                Log.v("HomeActivity", "Connected")
            } else {
                Log.v("HomeActivity", "Not Connected")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun addName(){

        val preferences = requireContext().getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)
        val nameText: TextView = v.textName
        if (preferences.getInt(gender, -1) == 1)
            nameText.text = "Hello\nMr. " + preferences.getString(name,"")
        else if (preferences.getInt(gender, -1) == 2)
            nameText.text = "Hello\nMs. " + preferences.getString(name,"")
        else if (preferences.getInt(gender, -1) == 0)
            nameText.text = "Hello\n" + preferences.getString(name,"")
        else
            nameText.text = ""
        nameText.setOnClickListener {
            setName()
        }
    }

    private fun setName() {

        val layoutName = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_name, null)
        val buildAlert = AlertDialog.Builder(requireContext()).setView(layoutName).show()
        val prefences = requireContext().getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)
        val editor = prefences.edit()
        val nameText: TextInputEditText = layoutName.findViewById(R.id.editTextName)
        val radioGroup: RadioGroup = layoutName.findViewById(R.id.typeRadioGroup)

        layoutName.findViewById<Button>(R.id.buttonDone).setOnClickListener {
            editor.putString(name, nameText.text.toString())

            if (radioGroup.checkedRadioButtonId == R.id.hidden)
                editor.putInt(gender, 0)
            else if (radioGroup.checkedRadioButtonId == R.id.male)
                editor.putInt(gender, 1)
            else if (radioGroup.checkedRadioButtonId == R.id.female)
                editor.putInt(gender, 2)
            else
                editor.putInt(gender, -1)
            editor.apply()

            buildAlert.dismiss()
            addName()
        }
        layoutName.findViewById<Button>(R.id.buttonSkip).setOnClickListener {
            buildAlert.dismiss()
        }
    }

    //companion object {    private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1  }

    private fun recyclerView(base: String) {

        val adapter = SpendingListAdapter(requireContext(), base)
        val recyclerview = v.recyclerView
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val currencyConverter = CurrencyConverter(requireContext())

        spendingViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)
        spendingViewModel.readAllData.observe(requireActivity(), { spending ->
            adapter.setData(spending)
            var value = 0.0
            for(spent in spending) {
                if(spent.currency != base)
                    value += currencyConverter.convert(spent.currency, base, spent.cost)
                else
                    value += spent.cost
                Log.v("HomeFragment",value.toString())
            }
            Log.v("HomeFragment son",value.toString())
            v.textTotalAmount.setText(String.format("%.2f", value))
            v.textTotalCurrency.setText(base)
        })

        v.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_insertFragment)
        }
    }

    @Suppress("DEPRECATION")
    fun changeBase(view: View) {
        when (view.id) {
            R.id.buttonTRY -> {
                buttonTRY.setTextColor(resources.getColor(R.color.white))
                buttonTRY.setBackgroundColor(resources.getColor(R.color.lightred))
                buttonUSD.setTextColor(resources.getColor(R.color.black))
                buttonUSD.setBackgroundColor(resources.getColor(R.color.blue))
                buttonEUR.setTextColor(resources.getColor(R.color.black))
                buttonEUR.setBackgroundColor(resources.getColor(R.color.green))
                buttonGBP.setTextColor(resources.getColor(R.color.black))
                buttonGBP.setBackgroundColor(resources.getColor(R.color.yellow))
                recyclerView("TRY")}
            R.id.buttonUSD -> {
                buttonTRY.setTextColor(resources.getColor(R.color.black))
                buttonTRY.setBackgroundColor(resources.getColor(R.color.red))
                buttonUSD.setTextColor(resources.getColor(R.color.white))
                buttonUSD.setBackgroundColor(resources.getColor(R.color.lightblue))
                buttonEUR.setTextColor(resources.getColor(R.color.black))
                buttonEUR.setBackgroundColor(resources.getColor(R.color.green))
                buttonGBP.setTextColor(resources.getColor(R.color.black))
                buttonGBP.setBackgroundColor(resources.getColor(R.color.yellow))
                recyclerView("USD")}
            R.id.buttonEUR -> {
                buttonTRY.setTextColor(resources.getColor(R.color.black))
                buttonTRY.setBackgroundColor(resources.getColor(R.color.red))
                buttonUSD.setTextColor(resources.getColor(R.color.black))
                buttonUSD.setBackgroundColor(resources.getColor(R.color.blue))
                buttonEUR.setTextColor(resources.getColor(R.color.white))
                buttonEUR.setBackgroundColor(resources.getColor(R.color.lightgreen))
                buttonGBP.setTextColor(resources.getColor(R.color.black))
                buttonGBP.setBackgroundColor(resources.getColor(R.color.yellow))
                recyclerView("EUR")}
            R.id.buttonGBP -> {
                buttonTRY.setTextColor(resources.getColor(R.color.black))
                buttonTRY.setBackgroundColor(resources.getColor(R.color.red))
                buttonUSD.setTextColor(resources.getColor(R.color.black))
                buttonUSD.setBackgroundColor(resources.getColor(R.color.blue))
                buttonEUR.setTextColor(resources.getColor(R.color.black))
                buttonEUR.setBackgroundColor(resources.getColor(R.color.green))
                buttonGBP.setTextColor(resources.getColor(R.color.white))
                buttonGBP.setBackgroundColor(resources.getColor(R.color.lightyellow))
                recyclerView("GBP")}
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            changeBase(view)
        }
    }
}