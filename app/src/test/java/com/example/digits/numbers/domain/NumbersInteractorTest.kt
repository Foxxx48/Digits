package com.example.digits.numbers.domain

import com.example.digits.details.data.NumberFactDetails
import com.example.digits.numbers.presentation.ManageResources
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NumbersInteractorTest {
    private lateinit var interactor: NumbersInteractor
    private lateinit var repository: TestNumbersRepository
    private lateinit var manageResources: TestManagerResources

    @Before
    fun setup() {
        manageResources = TestManagerResources()
        repository = TestNumbersRepository()
        interactor = NumbersInteractor.Base(
            repository,
            HandleRequest.Base(HandleError.Base(manageResources), repository),
            NumberFactDetails.Base()
        )
    }

    @Test
    fun test_init_success() = runBlocking {
        repository.changeExpectedList(listOf(NumberFact("7", "fact of number 7")))

        val actual = interactor.init()

        val expected = NumbersResult.Success(listOf(NumberFact("7", "fact of number 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.allNumbersCalledCount)
    }

    @Test
    fun test_fact_about_number_success(): Unit = runBlocking {
        repository.changeExpectedFactOfNumber(NumberFact("77", "fact about 77"))

        val actual = interactor.factAboutNumber("77")

        val expected =  NumbersResult.Success(listOf(NumberFact("77", "fact about 77")))

        assertEquals(expected, actual)
        assertEquals("77", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun test_fact_about_number_error(): Unit = runBlocking {
        repository.expectingErrorGetFact(true)
        manageResources.changeExpected("no internet connection")

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure("no internet connection")

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun test_fact_about_random_number_success(): Unit = runBlocking {
        repository.changeExpectedFactOfRandomNumber(NumberFact("7", "fact about 7"))


        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Success(listOf(NumberFact("7", "fact about 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }

    @Test
    fun test_fact_about_random_number_error(): Unit = runBlocking {
        repository.expectingErrorGetRandomFact(true)
        manageResources.changeExpected("no internet connection")

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Failure("no internet connection")

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)

    }

    private class TestNumbersRepository : NumbersRepository {


        private val allNumbers = mutableListOf<NumberFact>()
        private var numberFact = NumberFact("", "")
        private var errorWhileNumberFact = false

        var allNumbersCalledCount = 0
        val numberFactCalledList = mutableListOf<String>()
        val randomNumberFactCalledList = mutableListOf<String>()

        fun changeExpectedList(list: List<NumberFact>) {
            allNumbers.clear()
            allNumbers.addAll(list)
        }

        fun changeExpectedFactOfNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun changeExpectedFactOfRandomNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun expectingErrorGetFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        fun expectingErrorGetRandomFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        override suspend fun allNumbers(): List<NumberFact> {
            allNumbersCalledCount++
            return allNumbers
        }

        override suspend fun numberFact(number: String): NumberFact {
            numberFactCalledList.add(number)
            if (errorWhileNumberFact)
                throw NoInternetConnectionException()
            allNumbers.add(numberFact)
            return numberFact
        }

        override suspend fun randomNumberFact(): NumberFact {
            randomNumberFactCalledList.add("")
            if (errorWhileNumberFact)
                throw NoInternetConnectionException()
            allNumbers.add(numberFact)
            return numberFact
        }

    }

    private class TestManagerResources : ManageResources {
        var value: String = ""

        fun changeExpected(expected: String) {
            value = expected
        }

        override fun string(id: Int): String {
            return value
        }

    }

}