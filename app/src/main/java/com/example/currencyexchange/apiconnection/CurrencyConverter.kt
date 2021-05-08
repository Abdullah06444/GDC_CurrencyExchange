package com.example.currencyexchange.apiconnection

import android.content.Context

class CurrencyConverter(val context: Context) {

    val KEY_BASE = "BASE"
    val KEY_EUR = "EUR"
    val KEY_TRY = "TRY"
    val KEY_USD = "USD"
    val KEY_GBP = "GBP"

    fun convert(base: String, target: String, value: Double): Double {

        val prefences = context.getSharedPreferences("com.example.currencyexchange", Context.MODE_PRIVATE)
        val baseValueRate = prefences.getFloat(
            when(base){
                "TRY" -> KEY_TRY
                "USD" -> KEY_USD
                "EUR" -> KEY_EUR
                "GBP" -> KEY_GBP
                else -> "KEY_BASE"
            }, when(base){
                "TRY" -> 10.0f
                "USD" -> 1.2f
                "GBP" -> 0.9f
                "EUR" -> 1.0f
                else -> 1.0f
            })

        val eurValue = value / baseValueRate

        val targetValueRate = prefences.getFloat(
            when(target){
                "TRY" -> KEY_TRY
                "USD" -> KEY_USD
                "GBP" -> KEY_GBP
                "EUR" -> KEY_EUR
                else -> "KEY_BASE"
            }, when(base){
                "TRY" -> 10.0f
                "USD" -> 1.2f
                "GBP" -> 0.9f
                "EUR" -> 1.0f
                else -> 1.0f
            })

        return eurValue*targetValueRate
    }
}