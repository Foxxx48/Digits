package com.example.digits.numbers.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface CloudModule {
    fun <T> service(clast: Class<T>): T
    class Mock(private val randomApiHeader: RandomApiHeader.MockResponse) : CloudModule {
        override fun <T> service(clast: Class<T>): T = MockNumbersService(randomApiHeader) as T
    }
    class Base : CloudModule {
        override fun <T> service(clast: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(clast)
        }
    }
    companion object {
        private val BASE_URL = "http://numbersapi.com/"
    }
}