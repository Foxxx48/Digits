package com.example.digits.details.presentation

import androidx.lifecycle.ViewModel
import com.example.digits.details.data.NumberFactDetails

class NumberDetailsViewModel(
    private val data: NumberFactDetails.Read
) : ViewModel(), NumberFactDetails.Read {

    override fun read(): String = data.read()
}