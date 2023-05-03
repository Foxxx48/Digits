package com.example.digits.main.sl

import com.example.digits.main.presentations.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {
    override fun viewModel() = MainViewModel(
        core.provideWorkManagerWrapper(),
        core.provideNavigation())
}