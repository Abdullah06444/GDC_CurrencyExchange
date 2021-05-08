package com.example.currencyexchange.apiconnection

import android.content.Context
import android.util.Log

class CurrencyConverter(val context: Context)  {

    var baseValueRate = 1.00f
    fun convert(base: String, target: String, value: Double): Double {

        val prefences = context.getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)

        if (target == "TRY" && base == "USD")
        {   baseValueRate = prefences.getFloat("convert?q=USD_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",8.442160f)
            Log.v("CurrencyConverter", "Succesfully Added from TRY to USD")
        }
        if (target == "TRY" && base == "EUR")
        {   baseValueRate = prefences.getFloat("convert?q=EUR_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",10.314591f)
            Log.v("CurrencyConverter", "Succesfully Added from TRY to EUR")
        }
        if (target == "TRY" && base == "GBP")
        {   baseValueRate = prefences.getFloat("convert?q=GBP_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",11.937829f)
            Log.v("CurrencyConverter", "Succesfully Added from TRY to GBP")
        }
        if (target == "USD" && base == "TRY")
        {   baseValueRate = prefences.getFloat("convert?q=TRY_USD&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",0.118472f)
            Log.v("CurrencyConverter", "Succesfully Added from USD to TRY")
        }
        if (target == "USD" && base == "EUR")
        {   baseValueRate = prefences.getFloat("convert?q=EUR_USD&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",1.221710f)
            Log.v("CurrencyConverter", "Succesfully Added from USD to EUR")
        }
        if (target == "USD" && base == "GBP")
        {   baseValueRate = prefences.getFloat("convert?q=GBP_USD&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",1.414367f)
            Log.v("CurrencyConverter", "Succesfully Added from USD to GBP")
        }
        if (target == "EUR" && base == "TRY")
        {   baseValueRate = prefences.getFloat("convert?q=TRY_EUR&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",0.096942f)
            Log.v("CurrencyConverter", "Succesfully Added from EUR to TRY")
        }
        if (target == "EUR" && base == "USD")
        {   baseValueRate = prefences.getFloat("convert?q=USD_EUR&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",0.818530f)
            Log.v("CurrencyConverter", "Succesfully Added from EUR to USD")
        }
        if (target == "EUR" && base == "GBP")
        {   baseValueRate = prefences.getFloat("convert?q=GBP_EUR&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",1.157373f)
            Log.v("CurrencyConverter", "Succesfully Added from EUR to GBP")
        }
        if (target == "GBP" && base == "TRY")
        {   baseValueRate = prefences.getFloat("convert?q=TRY_GBP&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",0.083742f)
            Log.v("CurrencyConverter", "Succesfully Added from GBP to TRY")
        }
        if (target == "GBP" && base == "USD")
        {   baseValueRate = prefences.getFloat("convert?q=USD_GBP&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",0.706550f)
            Log.v("CurrencyConverter", "Succesfully Added from GBP to USD")
        }
        if (target == "GBP" && base == "EUR")
        {   baseValueRate = prefences.getFloat("convert?q=EUR_GBP&compact=ultra&apiKey=d671b6e4fa740cb4ec4f",0.864026f)
            Log.v("CurrencyConverter", "Succesfully Added from GBP to EUR")
        }
        return value*baseValueRate
    }
}
