package com.example.digits.main

import android.app.Application
import android.util.Log
import com.example.digits.BuildConfig
import com.example.digits.numbers.data.CloudModule
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
        val cloudModule = if (BuildConfig.DEBUG)
            CloudModule.Debug()
        else
            CloudModule.Release()
    }
}