package com.example.myfirstapplication

import retrofit2.http.GET
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/markets")
    suspend fun getCoins(@Query("vs_currency") currency: String = "inr"): List<Coin>
}