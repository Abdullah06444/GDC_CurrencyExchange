package com.example.currencyexchange.database

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyexchange.apiconnection.CurrencyConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.SpendingListBinding

class SpendingViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Spending>>
    private val repository: SpendingRepository

    init {
        val spendingDao = SpendingRoomDB.getDatabase(application).SpendingDao()
        repository = SpendingRepository(spendingDao)
        readAllData = repository.readAllData
    }

    fun addSpending(spending: Spending) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSpending(spending)
        }
    }

    fun deleteSpending(spending: Spending) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSpending(spending)
        }
    }
}

class SpendingRepository(private val spendingDao: SpendingDao) {
    val readAllData: LiveData<List<Spending>> = spendingDao.readAllData()

    suspend fun addSpending(spending: Spending) {
        spendingDao.insert(spending)
    }

    suspend fun deleteSpending(spending: Spending){
        spendingDao.deleteSpending(spending)
    }
}

@Database(entities = [Spending::class], version = 1, exportSchema = false)
abstract class SpendingRoomDB: RoomDatabase(){
    abstract fun SpendingDao(): SpendingDao

    companion object{
        @Volatile
        private  var INSTANCE: SpendingRoomDB? = null

        fun getDatabase(context: Context): SpendingRoomDB{

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpendingRoomDB::class.java,
                    "spending_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

class SpendingListAdapter(val context: Context, val base: String):
    RecyclerView.Adapter<SpendingListAdapter.MyViewHolder>() {

    private var spendingList = emptyList<Spending>()

    class MyViewHolder(val binding: SpendingListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(SpendingListBinding.inflate(LayoutInflater.from(p0.context), p0, false))
    }

    override fun getItemCount(): Int {
        return spendingList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currencyConverter = CurrencyConverter(context)

        val currentSpending = spendingList[position]
        holder.binding.spendingExplanation.text = currentSpending.description.toString()
        holder.binding.spendingAmount.text = String.format("%.2f", currencyConverter.convert(currentSpending.currency, base, currentSpending.cost))
        holder.binding.spendingCurrency.text = base
        holder.binding.imageRow.setImageResource(
            when(currentSpending.type){
                0 -> R.drawable.ic_shopping_extra
                1 -> R.drawable.ic_bill
                2 -> R.drawable.ic_rent
                else -> R.drawable.ic_shopping_extra
            }
        )

        holder.binding.imageRow.setColorFilter(
            ContextCompat.getColor(holder.itemView.context,
            when(currentSpending.type) {
                0 -> R.color.blue
                1 -> R.color.yellow
                2 -> R.color.green
                else -> R.color.blue
            }
        ), PorterDuff.Mode.SRC_IN)

        holder.binding.recyclerView.setOnClickListener{
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_deleteFragment)
        }
    }

    fun setData(spendings: List<Spending>){
        this.spendingList = spendings
        notifyDataSetChanged()
    }
}

