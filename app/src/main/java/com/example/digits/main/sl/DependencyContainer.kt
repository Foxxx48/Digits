package com.example.digits.main.sl

import androidx.lifecycle.ViewModel
import com.example.digits.details.presentation.NumberDetailsViewModel
import com.example.digits.details.sl.NumberDetailsModule
import com.example.digits.main.presentations.MainViewModel
import com.example.digits.numbers.domain.NumbersRepository
import com.example.digits.numbers.presentation.NumbersViewModel
import com.example.digits.numbers.sl.NumbersModule
import com.example.digits.numbers.sl.ProvideNumbersRepository
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

    ) : DependencyContainer, ProvideNumbersRepository {

        private val repository: NumbersRepository by lazy {
            ProvideNumbersRepository.Base(core).provideNumbersRepository()
        }

        override fun <T : ViewModel> module(clast: Class<T>): Module<*> = when (clast) {
            MainViewModel::class.java -> MainModule(core)
            NumbersViewModel.Base::class.java -> NumbersModule(core, this)
            NumberDetailsViewModel::class.java -> NumberDetailsModule(core)
            else -> dependencyContainer.module(clast)
        }

        override fun provideNumbersRepository(): NumbersRepository = repository
    }

}