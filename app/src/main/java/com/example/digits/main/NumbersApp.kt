package com.example.digits.main

import android.app.Application
import android.util.Log
import com.example.digits.numbers.data.NumbersCloudDataSource
import com.example.digits.numbers.data.NumbersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(GsonConverterFactory.create())

            .client(client).build()

        val service = retrofit.create(NumbersService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val dataSource = NumbersCloudDataSource.Base(service)
            val fact = dataSource.randomNumber()
            println(fact)
            Log.d("MYApp", "RandomNumberFact$fact")
        }

    }
}