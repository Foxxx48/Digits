package com.example.digits.numbers.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

interface CloudModule {

    fun <T> service(clasz: Class<T>): T

    abstract class Abstract : CloudModule {

        protected abstract val level: HttpLoggingInterceptor.Level
        protected open val baseUrl: String = "http://numbersapi.com/"

        override fun <T> service(clasz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply {
                setLevel(level)
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(clasz)
        }
    }

    class Debug : Abstract() {
        override val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
    }
    class Release : Abstract() {
        override val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE
    }
}