package com.example.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey : String = API_KEY
    ):ResponseBody

    companion object{
        const val API_KEY="FI4ZJWWCWQ9G7ZYC"
        const val BASE_URL="https://alphavantage.co"
    }

}