package com.example.digits.details.sl

import com.example.digits.details.presentation.NumberDetailsViewModel
import com.example.digits.main.sl.Module
import com.example.digits.main.sl.ProvideNumberDetails

class NumberDetailsModule(
    private val provideNumberDetails: ProvideNumberDetails
) : Module<NumberDetailsViewModel> {

    override fun viewModel() = NumberDetailsViewModel(provideNumberDetails.provideNumberDetails())
}