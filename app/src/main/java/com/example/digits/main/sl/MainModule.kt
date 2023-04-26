package com.example.digits.main.sl

import com.example.digits.main.presentations.MainViewModel

class MainModule(private val navigation: ProvideNavigation) : Module<MainViewModel> {

    override fun viewModel() = MainViewModel(
            navigation.provideNavigation()
    )
}