package com.example.currencyexchange.apiconnection

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyApi(val context: Context)  {

    //val KEY_BASE = "BASE"
    val KEY_TRY = "TRY"
    val KEY_USD = "USD"
    val KEY_EUR = "EUR"
    val KEY_GBP = "GBP"

    fun getData(){

        val prefences = context.getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)
        val editor = prefences.edit()
        //http://api.exchangeratesapi.io/v1/latest?access_key=e05dc8f35f7597964b39f6627eb0cdc4
        //https://free.currconv.com/api/v7/convert?q=USD_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f

        val retrofit = Retrofit.Builder()
            .baseUrl("https://free.currconv.com/api/v7/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CurrencyService::class.java)

        val call = service.getCurrencyList("convert?q=USD_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f")
        Log.v("CurrencyApi",call.toString())
        call.enqueue(object : Callback<CurrencyModel> {
            override fun onResponse(call: Call<CurrencyModel>, response: Response<CurrencyModel>) {
                if (response.code() == 1) {
                    val currencyModel = response.body()!!
                    //editor.putString(KEY_BASE, currencyModel.base)
                    //editor.putFloat(KEY_EUR, 1.0F)
                    editor.putFloat(KEY_TRY, currencyModel.rates.TRY.toString().toFloat())
                    editor.putFloat(KEY_USD, currencyModel.rates.USD.toString().toFloat())
                    editor.putFloat(KEY_EUR, currencyModel.rates.EUR.toString().toFloat())
                    editor.putFloat(KEY_GBP, currencyModel.rates.GBP.toString().toFloat())
                    //editor.putFloat("convert?q=USD_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f", currencyModel.rates.USD.toString().toFloat())

                    editor.apply()
                }
            }
            override fun onFailure(call: Call<CurrencyModel>, t: Throwable) {}
        })
    }
}