package com.example.digits.main.sl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

interface ProvideViewModel {
    fun <T : ViewModel> provideViewModel(clast: Class<T>, owner: ViewModelStoreOwner): T
}