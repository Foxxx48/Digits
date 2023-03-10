package com.example.digits.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digits.R
import com.example.digits.numbers.domain.NumbersInteractor
import kotlinx.coroutines.launch

interface NumbersViewModel : FetchNumbers, ObserveNumbers {
    class Base(
        private val handleNumbersRequest: HandleNumbersRequest,
        private val manageResources: ManageResources,
        private val communications: NumbersCommunications,
        private val interactor: NumbersInteractor,

    ) : ViewModel(), NumbersViewModel {
        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
            communications.observeProgress(owner, observer)
        }

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
            communications.observeState(owner, observer)
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            communications.observeList(owner, observer)
        }

        override fun fetchNumberFact(number: String) {
            if (number.isEmpty()) {
                communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
            } else {
                handleNumbersRequest.handle(viewModelScope) {
                    interactor.factAboutNumber(number)
                }
            }
        }

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) {
                handleNumbersRequest.handle(viewModelScope) {
                    interactor.init()
                }
            }
        }

        override fun fetchRandomNumberFact() =
            handleNumbersRequest.handle(viewModelScope) {
                interactor.factAboutRandomNumber()
            }

    }
}

interface FetchNumbers {
    fun fetchNumberFact(number: String)

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()
}
