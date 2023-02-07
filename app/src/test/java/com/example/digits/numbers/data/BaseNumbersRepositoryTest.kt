package com.example.digits.numbers.data

import com.example.digits.numbers.domain.HandleError
import com.example.digits.numbers.domain.NoInternetConnectionException
import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumbersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseNumbersRepositoryTest {

    private lateinit var cacheDataSource: TestNumbersCacheDataSource
    private lateinit var cloudDataSource: TestNumbersCloudDataSource
    private lateinit var repository: NumbersRepository



    @Before
    fun setup() {
        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        val mapper = NumberDataToDomain()
        repository = BaseNumbersRepository(
            cloudDataSource,
            cacheDataSource,
            HandleDataRequest.Base(
                cacheDataSource, mapper,
                HandleDomainError()
            ),
            mapper
        )
    }

    @Test
    fun test_all_numbers() = runBlocking {

        cacheDataSource.replaceData(
            listOf(
                NumberData("5", "fact of 5"),
                NumberData("6", "fact of 6")
            )
        )

        val actual = repository.allNumbers()
        val expected = listOf(
            NumberFact("5", "fact of 5"),
            NumberFact("6", "fact of 6")
        )

        actual.forEachIndexed { index, item ->
            assertEquals(expected[index], item)
        }
        assertEquals(1, cacheDataSource.allNumbersCalledCount)
    }

    @Test
    fun test_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.changeExpected(NumberData("3", "fact of 3"))
        cacheDataSource.replaceData(emptyList())


        val actual = repository.numberFact("3")
        val expected = NumberFact("3", "fact of 3")

        assertEquals(expected, actual)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(1, cloudDataSource.numberCalledCount)

        assertEquals(0, cacheDataSource.numberCalledList.size)

        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(NumberData("3", "fact of 3"), cacheDataSource.data[0])
    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_number_fact_not_cached_no_connection() = runBlocking {

        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.numberFact("3")

        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(1, cloudDataSource.numberCalledCount)

        assertEquals(0, cacheDataSource.numberCalledList.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun test_number_fact_cached_success() = runBlocking {
        cloudDataSource.changeConnection(true)
        cloudDataSource.changeExpected(NumberData("3", "cloud 3"))
        cacheDataSource.replaceData(listOf(NumberData("3", "fact of 3")))

        val actual = repository.numberFact("3")
        val expected = NumberFact("3", "fact of 3")

        assertEquals(expected, actual)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(0, cloudDataSource.numberCalledCount)

        assertEquals(1, cacheDataSource.numberCalledList.size)
        assertEquals("3", cacheDataSource.numberCalledList[0])
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun test_random_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.changeExpected(NumberData("3", "fact of 3"))
        cacheDataSource.replaceData(emptyList())


        val actual = repository.randomNumberFact()
        val expected = NumberFact("3", "fact of 3")

        assertEquals(expected, actual)

        assertEquals(1, cloudDataSource.randomNumberCalledCount)
        assertEquals(0, cloudDataSource.numberCalledCount)

        assertEquals(0, cacheDataSource.numberCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(NumberData("3", "fact of 3"), cacheDataSource.data[0])
    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_random_number_fact_not_cached_not_connected() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        repository.randomNumberFact()

        assertEquals(0, cloudDataSource.numberCalledCount)
        assertEquals(1, cloudDataSource.randomNumberCalledCount)


        assertEquals(0, cacheDataSource.numberCalledList.size)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun test_random_number_fact_cached() = runBlocking {
        cloudDataSource.changeConnection(true)
        cloudDataSource.changeExpected(NumberData("3", "cloud 3"))
        cacheDataSource.replaceData(listOf(NumberData("3", "fact of 3")))

        val actual = repository.randomNumberFact()
        val expected = NumberFact("3", "cloud 3")

        assertEquals(expected, actual)
        assertEquals(1, cloudDataSource.randomNumberCalledCount)

        assertEquals(0, cacheDataSource.numberCalledList.size)

        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    private class TestNumbersCacheDataSource : NumbersCacheDataSource {

        var containsCalledList = mutableListOf<Boolean>()

        var numberCalledList = mutableListOf<String>()

        var allNumbersCalledCount = 0

        var saveNumberFactCalledCount = 0
        val saveNumberFactCalledList = mutableListOf<NumberData>()

        val data = mutableListOf<NumberData>()


        fun replaceData(newData: List<NumberData>) {
            data.clear()
            data.addAll(newData)
        }

        override suspend fun allNumbers(): List<NumberData> {
            allNumbersCalledCount++
            return data
        }

        override suspend fun contains(number: String): Boolean {
            val result = data.find { it.map(NumberData.Mapper.Matches(number)) } != null
            containsCalledList.add(result)
            return result
        }

        override suspend fun number(number: String): NumberData {
            numberCalledList.add(number)
            return data[0]
        }

        override suspend fun saveNumber(numberData: NumberData) {
            saveNumberFactCalledList.add(numberData)
            saveNumberFactCalledCount++
            data.add(numberData)
        }
    }

    private class TestNumbersCloudDataSource : NumbersCloudDataSource {

        private var isConnection = true
        private var numberData = NumberData("", "")
        private val numberCalledList = mutableListOf<String>()
        private val randomNumberFactCalledList = mutableListOf<String>()
        var numberCalledCount = 0
        var randomNumberCalledCount = 0

        fun changeConnection(connected: Boolean) {
            isConnection = connected
        }

        fun changeExpected(newNumberData: NumberData) {
            numberData = newNumberData
        }

        override suspend fun number(number: String): NumberData {
            return if (isConnection) {
                numberCalledCount++
                numberCalledList.add(number)
                numberData
            } else {
                throw UnknownHostException()
            }
        }


        override suspend fun randomNumber(): NumberData {
            return if (isConnection) {
                randomNumberCalledCount++
                numberData
            } else {
                throw UnknownHostException()
            }
        }
    }
}
