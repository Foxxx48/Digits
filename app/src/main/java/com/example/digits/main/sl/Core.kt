package com.example.digits.main.sl

import android.content.Context
import com.example.digits.numbers.data.cache.CacheModule
import com.example.digits.numbers.data.cache.NumbersDatabase
import com.example.digits.numbers.data.cloud.CloudModule
import com.example.digits.numbers.presentation.DispatchersList
import com.example.digits.numbers.presentation.ManageResources

interface Core : CloudModule, CacheModule, ManageResources {
    fun provideDispatchers(): DispatchersList

    override fun <T> service(clast: Class<T>): T
   class Base(
        context: Context,
        private val provideInstances: ProvideInstances
    ) : Core {

        private val manageResources: ManageResources = ManageResources.Base(context)

        private val dispatchersList by lazy {
            DispatchersList.Base()
        }

        val cloudModule by lazy {
            provideInstances.provideCloudModule()
        }

        val cacheModule by lazy {
            provideInstances.provideCacheModule()
        }

        override fun provideDispatchers(): DispatchersList = dispatchersList

        override fun <T> service(clast: Class<T>): T = cloudModule.service(clast)

        override fun provideDatabase(): NumbersDatabase = cacheModule.provideDatabase()

        override fun string(id: Int): String = manageResources.string(id)
    }
}