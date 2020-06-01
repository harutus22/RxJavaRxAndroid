package com.example.rxjavarxandroid.service

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val retrofitBuilder by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val requestApi = retrofitBuilder.create(RequestApi::class.java)

    fun getRequestApi() = requestApi
}