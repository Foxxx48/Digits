package com.example.digits.main.sl

import android.content.Context
import com.example.digits.numbers.data.cache.CacheModule
import com.example.digits.numbers.data.cloud.CloudModule
import com.example.digits.numbers.data.cloud.RandomApiHeader

interface ProvideInstances : ProvideRandomApiHeader {

    fun provideCacheModule(): CacheModule
    fun provideCloudModule(): CloudModule

    class Release(private val context: Context) : ProvideInstances {
        override fun provideCacheModule() = CacheModule.Base(context)
        override fun provideCloudModule() = CloudModule.Base()
        override fun provideRandomApiHeader() = RandomApiHeader.Base()
    }

    class Mock(private val context: Context) : ProvideInstances {
        override fun provideCacheModule() = CacheModule.Mock(context)
        override fun provideCloudModule() = CloudModule.Mock(provideRandomApiHeader())
        override fun provideRandomApiHeader() = RandomApiHeader.Mock()
    }
}

interface ProvideRandomApiHeader {
    fun provideRandomApiHeader(): RandomApiHeader.Combo
}