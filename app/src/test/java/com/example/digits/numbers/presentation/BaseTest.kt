package com.example.digits.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.digits.main.presentations.NavigationCommunication
import com.example.digits.main.presentations.NavigationStrategy

abstract class BaseTest {

    protected class TestNavigationCommunication : NavigationCommunication.Mutable {

        lateinit var strategy: NavigationStrategy
        var count = 0
        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationStrategy>) = Unit

        override fun map(source: NavigationStrategy) {
            strategy = source
            count++
        }
    }

    protected class TestNumbersCommunications : NumbersCommunications {
        val progressCalledList = mutableListOf<Int>()
        val stateCalledList = mutableListOf<UiState>()
        var timeShowList = 0
        val numbersList = mutableListOf<NumberUi>()


        override fun showProgress(show: Int) {
            progressCalledList.add(show)

        }

        override fun showState(uiState: UiState) {
            stateCalledList.add(uiState)
        }

        override fun showList(list: List<NumberUi>) {
            timeShowList++
            numbersList.addAll(list)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>)  = Unit
    }

}