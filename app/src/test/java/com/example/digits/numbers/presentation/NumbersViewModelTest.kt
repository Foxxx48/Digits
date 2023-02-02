package com.example.digits.numbers.presentation

import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumberUiMapper
import com.example.digits.numbers.domain.NumbersInteractor
import com.example.digits.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : BaseTest() {

    private lateinit var communications: TestNumbersCommunications
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var testManageResources: TestManagerResources
    private lateinit var viewModel: NumbersViewModel
    private lateinit var dispatchersList: DispatchersList

    @Before
    fun init() {

        dispatchersList = TestDispatcherList()
        communications = TestNumbersCommunications()
        interactor = TestNumbersInteractor()
        testManageResources = TestManagerResources()

        viewModel = NumbersViewModel.Base(
            HandleNumbersRequest.Base(
                dispatchersList,
                communications,
                NumbersResultMapper(
                    communications, NumberUiMapper()
                )
            ),
            testManageResources,
            communications,
            interactor
        )
    }

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-unit and wait for the result
     */
    @Test
    fun `test init and re-init`() = runBlocking {

        interactor.changeExpectedResult(NumbersResult.Success())

        viewModel.init(isFirstRun = true)


        assertEquals(true, communications.progressCalledList[0])
        assertEquals(1, interactor.initCalledList.size)



        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timeShowList)

//        // get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

//        assertEquals(4, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("no internet connection"), communications.stateCalledList[1])

        assertEquals(0, communications.timeShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timeShowList)
    }

    /**
     * try to get information about empty number
     */
    @Test
    fun `fact about empty number`() = runBlocking {
        testManageResources.makeExpectedAnswer("entered number is empty")
        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)


        assertEquals(UiState.Error("entered number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timeShowList)

    }

    /**
     * try to get information about empty number
     */
    @Test
    fun `fact about some number`() = runBlocking {

        interactor.changeExpectedResult(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45")))
        )

        viewModel.fetchNumberFact("45")


        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)

        assertEquals(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))),
            interactor.fetchAboutNumberCalledList[0]
        )

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(1, communications.timeShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }

    private class TestManagerResources : ManageResources {
        var string: String = ""

        fun makeExpectedAnswer(expected: String) {
            string = expected
        }

        override fun string(id: Int): String {
            return string
        }

    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()


        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult

        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }



        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }

    }

    private class TestDispatcherList(
        private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }


}