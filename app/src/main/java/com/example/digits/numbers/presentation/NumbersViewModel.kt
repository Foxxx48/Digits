package com.example.digits.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.digits.numbers.domain.NumbersInteractor

class NumbersViewModel (
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor
) : ObserveNumbers  {
    fun fetchFact(s: String) {

    }

    fun init(isFirstRun: Boolean) {

    }

    fun fetchRandomNumberData() {
        TODO("Not yet implemented")
    }

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
        communications.observeProgress(owner, observer)
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
       communications.observeState(owner, observer)
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<NumberUi>) {
        communications.observeList(owner, observer)
    }

}