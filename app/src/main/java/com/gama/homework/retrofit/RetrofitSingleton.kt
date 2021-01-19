package com.gama.homework.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class RetrofitSingleton private constructor() {
    private val client: RetrofitAPI
    private val BASE_URL = "http://private-222d3-homework5.apiary-mock.com/api/"

    fun provideClient(): RetrofitAPI {
        return client
    }

    companion object {
        @Volatile
        private var instance: RetrofitSingleton? = null
        private lateinit var context: Context
        fun getInstance(context: Context?): RetrofitSingleton? {
            if (context != null) {
                Companion.context = context
            }
            if (instance == null) {
                synchronized(RetrofitSingleton::class.java) {
                    if (instance == null) {
                        instance = RetrofitSingleton()
                    }
                }
            }
            return instance
        }
    }

    init {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES).addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                            .addHeader(
                                    "IMSI", "357175048449937"
                            ).addHeader(
                                    "IMEI", "510110406068589")
                            .addHeader("Content-Type", "application/json")
                            .build()
                    chain.proceed(newRequest)
                }.build()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        client = retrofit.create(RetrofitAPI::class.java)
    }
}