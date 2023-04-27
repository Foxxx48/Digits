package com.example.digits.numbers.presentation

import android.view.View
import com.example.digits.main.presentations.NavigationStrategy
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

    private lateinit var navigation: TestNavigationCommunication
    private lateinit var communications: TestNumbersCommunications
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var testManageResources: TestManagerResources
    private lateinit var viewModel: NumbersViewModel
    private lateinit var dispatchersList: DispatchersList

    @Before
    fun init() {
        navigation = TestNavigationCommunication()
        dispatchersList = TestDispatcherList()
        communications = TestNumbersCommunications()
        interactor = TestNumbersInteractor()
        testManageResources = TestManagerResources()
        val detailsMapper = TestUiMapper()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

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
            interactor,
            navigation,
            detailsMapper
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


        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(1, interactor.initCalledList.size)



        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timeShowList)

//        // get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

//        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.VISIBLE, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.ShowError("no internet connection"), communications.stateCalledList[1])

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


        assertEquals(UiState.ShowError("entered number is empty"), communications.stateCalledList[0])

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


        assertEquals(View.VISIBLE, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)

        assertEquals(
            NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))),
            interactor.fetchAboutNumberCalledList[0]
        )

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)

        assertEquals(1, communications.timeShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }
    @Test
    fun `test clear error`() {
        viewModel.clearError()

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.ClearError)
    }


    @Test
    fun `test navigation details`() {
        viewModel.showDetails(NumberUi("0", "fact"))

        assertEquals("0 fact", interactor.details)
        assertEquals(1, navigation.count)
        assertEquals(true, navigation.strategy is NavigationStrategy.Add)
//        assertEquals(NavigationStrategy.Add(Screen.Details), navigation.strategy)
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

        var details: String = ""

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult

        }

        override fun saveDetails(details: String) {
            this.details = details
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

    private class TestUiMapper : NumberUi.Mapper<String> {
        override fun map(id: String, fact: String): String = "$id $fact"
    }
}


