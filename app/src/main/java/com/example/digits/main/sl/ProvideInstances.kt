package com.example.digits.main.sl

import android.content.Context
import com.example.digits.numbers.data.cache.CacheModule
import com.example.digits.numbers.data.cloud.CloudModule

interface ProvideInstances {

    fun provideCacheModule(): CacheModule
    fun provideCloudModule(): CloudModule

    class Release(private val context: Context) : ProvideInstances {
        override fun provideCacheModule(): CacheModule = CacheModule.Base(context)
        override fun provideCloudModule(): CloudModule = CloudModule.Base()
    }

    class Mock(private val context: Context) : ProvideInstances {
        override fun provideCacheModule(): CacheModule = CacheModule.Mock(context)
        override fun provideCloudModule(): CloudModule = CloudModule.Mock()
    }
}