package com.example.digits.numbers.presentation

import org.junit.Assert.*
import org.junit.Test

class NumbersViewModelTest {

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-unit and wait for the result
     */
    @Test
    fun `test init and re-init`(/*dependencies */) {
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)
        interactor.changeExpectedResult(NumbersResult.Success())

        viewModel.init(isFirstRun = true)

        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(NumbersResult.Success(), communications.stateCalledList[0])

        assertEquals(emptyList<NumberUi>(), communications.numbersList)

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        interactor.changeExpectedResult(NumbersResult.Failure())
        viewModel.fetchRandomNumberData()

        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        viewModel.init(isFirstRun = false)


    }

    private class TestNumbersCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<Boolean>()
        val numbersList = mutableListOf<NumberUi>()


        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)

        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)
        }

        override fun showList(list: List<NumberUi>) {
            numbersList.addAll(list)
        }
    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumberResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

    }


}