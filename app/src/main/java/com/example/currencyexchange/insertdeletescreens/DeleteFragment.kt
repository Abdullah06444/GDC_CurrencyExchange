package com.example.currencyexchange.insertdeletescreens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.currencyexchange.R
import com.example.currencyexchange.database.SpendingViewModel
import kotlinx.android.synthetic.main.fragment_delete.view.*
import kotlinx.android.synthetic.main.fragment_delete.view.buttonDone
import kotlinx.android.synthetic.main.fragment_delete.view.buttonSkip

class DeleteFragment : Fragment() {

    lateinit var v: View

    private val args by navArgs<DeleteFragmentArgs>()

    private lateinit var mSpendingViewModel: SpendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_delete, container, false)

        mSpendingViewModel = ViewModelProvider(this).get(SpendingViewModel::class.java)

        //getCurrentData()

        v.buttonSkip.setOnClickListener{
            findNavController().navigate(R.id.action_deleteFragment_to_homeFragment)
        }

        v.buttonDone.setOnClickListener{
            deleteAlert()
            getCurrentData()
        }

        return v
    }

    fun deleteAlert(){

        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("Delete Alert")
            setMessage("!!!Now, delete this spending!!!")
            setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener(function = positiveClick))
            setNeutralButton(android.R.string.no, null)
            show()
        }
    }

    val positiveClick = { dialog: DialogInterface, which: Int ->
        mSpendingViewModel.deleteSpending(args.currentSpending)
        Toast.makeText(requireContext(),"Succesfully Deleted", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_deleteFragment_to_homeFragment)
    }

    private fun getCurrentData() {

        v.itemSpendingExplanation.text = args.currentSpending.description.toString()
        v.itemSpendingAmount.text = String.format("%.2f", args.currentSpending.cost)
        v.itemSpendingCurrency.text = args.currentSpending.currency
        v.itemIcon.setImageResource(
            when(args.currentSpending.type){
                0 -> R.drawable.ic_shopping_extra
                1 -> R.drawable.ic_bill
                2 -> R.drawable.ic_rent
                else -> R.drawable.ic_shopping_extra
            }
        )

        v.itemIcon.setColorFilter(
            ContextCompat.getColor(v.context,
                when(args.currentSpending.type) {
                    0 -> R.color.blue
                    1 -> R.color.yellow
                    2 -> R.color.green
                    else -> R.color.blue
                }), android.graphics.PorterDuff.Mode.SRC_IN)

    }
}
