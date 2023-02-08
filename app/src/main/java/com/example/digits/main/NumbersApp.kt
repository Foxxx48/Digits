package com.example.digits.main

import android.app.Application
import com.example.digits.numbers.data.NumbersCloudDataSource
import com.example.digits.numbers.data.NumbersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val service = retrofit.create(NumbersService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val dataSource = NumbersCloudDataSource.Base(service)
            val fact = dataSource.number("10")
            println(fact)
        }

    }
}