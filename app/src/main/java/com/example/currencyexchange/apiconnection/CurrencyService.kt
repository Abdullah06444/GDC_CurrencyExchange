package com.example.currencyexchange.apiconnection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("latest")
    fun getCurrencyList(@Query("base") base : String) : Call<CurrencyModel>

//    @GET("latest")
//    fun getCurrencyList(@Query("convert?q=EUR_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f") base : String) : Call<CurrencyModel>

//    @GET("latest")
//    fun getCurrencyList(@Query("convert?q=GBP_TRY&compact=ultra&apiKey=d671b6e4fa740cb4ec4f") base : String) : Call<CurrencyModel>
}