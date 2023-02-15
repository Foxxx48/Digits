package com.example.digits.main

import android.app.Application
import com.example.digits.BuildConfig
import com.example.digits.numbers.data.cloud.CloudModule

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val cloudModule = if (BuildConfig.DEBUG)
            CloudModule.Debug()
        else
            CloudModule.Release()
    }
}