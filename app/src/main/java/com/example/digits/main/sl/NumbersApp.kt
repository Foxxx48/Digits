package com.example.digits.main.sl

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner


class NumbersApp : Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()

        viewModelsFactory = ViewModelsFactory(
            DependencyContainer.Base(
                Core.Base(this, true/*!BuildConfig.DEBUG*/)
            )
        )

    }

    override fun <T : ViewModel> provideViewModel(clast: Class<T>, owner: ViewModelStoreOwner): T =
        ViewModelProvider(owner, viewModelsFactory)[clast]

}