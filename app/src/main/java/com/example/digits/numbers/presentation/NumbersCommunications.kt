package com.example.digits.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumbersCommunications : ObserveNumbers {

    fun showProgress(show: Int)

    fun showState(uiState: UiState)

    fun showList(list: List<NumberUi>)

    class Base(
        private val progress: ProgressCommunication,
        private val state: NumbersStateCommunications,
        private val numbersList: NumbersListCommunication
    ) : NumbersCommunications {
        override fun showProgress(show: Int) = progress.map(show)

        override fun showState(uiState: UiState) = state.map(uiState)

        override fun showList(list: List<NumberUi>) = numbersList.map(list)
        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) {
            progress.observe(owner, observer)
        }

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
            state.observe(owner, observer)
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            numbersList.observe(owner, observer)
        }
    }
}

    interface ObserveNumbers {
        fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>)
        fun observeState(owner: LifecycleOwner, observer: Observer<UiState>)
        fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>)
    }

    interface ProgressCommunication : Communication.Mutable<Int> {
        class Base : Communication.Post<Int>(), ProgressCommunication
    }
    interface NumbersStateCommunications : Communication.Mutable<UiState> {
        class Base : Communication.Post<UiState>(), NumbersStateCommunications
    }
    interface NumbersListCommunication: Communication.Mutable<List<NumberUi>> {
        class Base : Communication.Post<List<NumberUi>>(), NumbersListCommunication

    }
