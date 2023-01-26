package com.example.digits.numbers.presentation

import com.example.digits.numbers.domain.NumbersInteractor

class NumbersViewModel(
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor
) {
    fun fetchFact(s: String) {

    }

    fun init(isFirstRun: Boolean) {

    }

    fun fetchRandomNumberData() {
        TODO("Not yet implemented")
    }

}