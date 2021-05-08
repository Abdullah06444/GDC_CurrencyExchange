package com.example.currencyexchange.apiconnection

data class CurrencyModel(
    val base: String,
    val date: String,
    val rates: Rates
)

data class Rates(

    val TRY: Double,
    val USD: Double,
    val GBP: Double,
    val EUR: Double
)