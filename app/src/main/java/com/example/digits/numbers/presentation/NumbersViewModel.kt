package com.example.digits.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digits.R
import com.example.digits.details.presentation.DetailsFragment
import com.example.digits.main.presentations.Init
import com.example.digits.main.presentations.NavigationCommunication
import com.example.digits.main.presentations.NavigationStrategy
import com.example.digits.numbers.domain.NumbersInteractor

interface NumbersViewModel : FetchNumbers, ObserveNumbers, ClearError, Init {
    fun showDetails(item: NumberUi)


    class Base(
        private val handleNumbersRequest: HandleNumbersRequest,
        private val manageResources: ManageResources,
        private val communications: NumbersCommunications,
        private val interactor: NumbersInteractor,
        private val navigationCommunication: NavigationCommunication.Mutate,
        private val detailsMapper: NumberUi.Mapper<String>

    ) : ViewModel(), NumbersViewModel {
        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) {
            communications.observeProgress(owner, observer)
        }

        override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
            communications.observeState(owner, observer)
        }

        override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            communications.observeList(owner, observer)
        }

        override fun clearError() {
            communications.showState(UiState.ClearError())
        }

        override fun showDetails(item: NumberUi) {
            navigationCommunication.map(
                NavigationStrategy.Add(DetailsFragment.newInstance(item.map(detailsMapper)))
            )
        }

        override fun fetchNumberFact(number: String) {
            if (number.isEmpty()) {
                communications.showState(UiState.ShowError(manageResources.string(R.string.empty_number_error_message)))
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
    fun fetchRandomNumberFact()
}

interface ClearError {
    fun clearError()
}
