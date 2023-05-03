package com.example.digits.main.sl

import android.content.Context
import com.example.digits.details.data.NumberFactDetails
import com.example.digits.main.presentations.NavigationCommunication
import com.example.digits.numbers.data.cache.CacheModule
import com.example.digits.numbers.data.cache.NumbersDatabase
import com.example.digits.numbers.data.cloud.CloudModule
import com.example.digits.numbers.data.cloud.RandomApiHeader
import com.example.digits.numbers.presentation.DispatchersList
import com.example.digits.numbers.presentation.ManageResources
import com.example.digits.random.WorkManagerWrapper

interface Core : CloudModule, CacheModule, ManageResources, ProvideNavigation, ProvideNumberDetails, ProvideRandomApiHeader, ProvideWorkManagerWrapper {
    fun provideDispatchers(): DispatchersList

    override fun <T> service(clast: Class<T>): T
    class Base(
        context: Context,
        private val provideInstances: ProvideInstances
    ) : Core {

        private val workManagerWrapper: WorkManagerWrapper = WorkManagerWrapper.Base(context)

        private val manageResources: ManageResources = ManageResources.Base(context)

        private val navigationCommunication = NavigationCommunication.Base()

        private val numberDetails = NumberFactDetails.Base()

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
        override fun provideNavigation() = navigationCommunication

        override fun provideNumberDetails(): NumberFactDetails.Mutable = numberDetails
        override fun provideRandomApiHeader(): RandomApiHeader.Combo = provideInstances.provideRandomApiHeader()
        override fun provideWorkManagerWrapper() = workManagerWrapper

    }
}
interface ProvideWorkManagerWrapper {
    fun provideWorkManagerWrapper(): WorkManagerWrapper
}

interface ProvideNavigation {
    fun provideNavigation(): NavigationCommunication.Mutable
}
interface ProvideNumberDetails {
    fun provideNumberDetails(): NumberFactDetails.Mutable
}