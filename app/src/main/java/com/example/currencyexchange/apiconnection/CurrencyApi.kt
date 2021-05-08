package com.example.currencyexchange.apiconnection

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyApi(val context: Context)  {

    val KEY_BASE = "BASE"
    val KEY_TRY = "TRY"
    val KEY_USD = "USD"
    val KEY_EUR = "EUR"
    val KEY_GBP = "GBP"

    fun getData(){

        val prefences = context.getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)
        val editor = prefences.edit()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.ratesapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyService::class.java)

        val call = service.getCurrencyList("")
        call.enqueue(object : Callback<CurrencyModel> {
            override fun onResponse(call: Call<CurrencyModel>, response: Response<CurrencyModel>) {
                if (response.code() == 200) {
                    val currencyModel = response.body()!!
                    editor.putString(KEY_BASE, currencyModel.base)
                    editor.putFloat(KEY_EUR, 1.0F)
                    editor.putFloat(KEY_TRY, currencyModel.rates.TRY.toString().toFloat())
                    editor.putFloat(KEY_USD, currencyModel.rates.USD.toString().toFloat())
                    //editor.putFloat(KEY_EUR, currencyModel.rates.EUR.toString().toFloat())
                    editor.putFloat(KEY_GBP, currencyModel.rates.GBP.toString().toFloat())
                    editor.apply()
                }
            }
            override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {

            }
        })
    }
}