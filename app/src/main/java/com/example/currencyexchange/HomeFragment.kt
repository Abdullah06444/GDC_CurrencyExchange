package com.example.currencyexchange

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_name.view.*

class HomeFragment : Fragment() {

    lateinit var v: View
    val gender = "GENDER"
    val name = "NAME"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        addName()
        return v
    }

    @SuppressLint("SetTextI18n")
    private fun addName(){

        val preferences = requireContext().getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)
        val nameText: TextView = v.textName
        val genderRadioButton = v.typeRadioGroup
        if (preferences.getInt(gender, -1) == 1)
            nameText.text = "Hello\nMr. " + preferences.getString(name,"")
        else if (preferences.getInt(gender, -1) == 2)
            nameText.text = "Hello\nMrs. " + preferences.getString(name,"")
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
}