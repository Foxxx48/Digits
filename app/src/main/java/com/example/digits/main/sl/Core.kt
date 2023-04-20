package com.example.digits.main.sl

import com.example.digits.BuildConfig
import com.example.digits.numbers.data.cloud.CloudModule

interface Core {

    class Base(private val isRelease: Boolean) : Core {

        val cloudModule by lazy {
            if (isRelease)
                CloudModule.Release()
            else
                CloudModule.Debug()
        }
    }
}