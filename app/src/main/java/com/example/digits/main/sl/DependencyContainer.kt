package com.example.digits.main.sl

import androidx.lifecycle.ViewModel
import com.example.digits.main.presentations.MainViewModel
import com.example.digits.numbers.presentation.NumbersViewModel
import com.example.digits.numbers.sl.NumbersModule
import java.lang.IllegalStateException

interface DependencyContainer {
    fun <T : ViewModel> module(clast: Class<T>): Module<*>

    class Error : DependencyContainer {
        override fun <T : ViewModel> module(clast: Class<T>): Module<*> {
            throw IllegalStateException("no module found for $clast")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()

    ) : DependencyContainer {

        override fun <T : ViewModel> module(clast: Class<T>): Module<*> = when (clast) {
            MainViewModel::class.java -> MainModule(core)
            NumbersViewModel.Base::class.java -> NumbersModule(core)
            else -> dependencyContainer.module(clast)
        }

    }
}