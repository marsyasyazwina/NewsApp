package com.marsya.idn.newsapp.Service

import com.google.android.gms.common.api.internal.ApiKey
import com.marsya.idn.newsapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//GET https://newsapi.org/v2/top-headlines?country=us&apiKey=be13eca672c446f58ef2e5e1384e7aa4

interface ApiService {
    @GET("top-headline")
    fun getNewsHeadLines(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ResponseNews>
}