package com.example.digits.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.digits.BuildConfig
import com.example.digits.numbers.domain.NumbersRepository
import com.example.digits.numbers.sl.ProvideNumbersRepository


class NumbersApp : Application(), ProvideViewModel, ProvideNumbersRepository {

    private lateinit var viewModelsFactory: ViewModelsFactory
    private lateinit var dependencyContainer: DependencyContainer.Base

    override fun onCreate() {
        super.onCreate()

        val provideInstances = if (!BuildConfig.DEBUG)
            ProvideInstances.Mock(this)
        else
            ProvideInstances.Release(this)

        dependencyContainer = DependencyContainer.Base(
            Core.Base(this,
                provideInstances)
        )

        viewModelsFactory = ViewModelsFactory(dependencyContainer)

    }

    override fun <T : ViewModel> provideViewModel(clast: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clast]

    override fun provideNumbersRepository(): NumbersRepository {
        return dependencyContainer.provideNumbersRepository()
    }

}