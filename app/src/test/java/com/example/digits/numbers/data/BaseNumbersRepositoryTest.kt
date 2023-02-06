package com.example.digits.numbers.data

import android.text.BoringLayout
import com.example.digits.numbers.domain.NoInternetConnectionException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseNumbersRepositoryTest {
    private lateinit var cacheDataSource: CacheNumbersDataSource
    private lateinit var cloudDataSource: CloudDataSource
    private lateinit var repository: NumbersRepository

    @Before
    fun setup() {
        cacheDataSource = TestCacheNumbersDataSource()
        cloudDataSource = TestCloudDataSource()
        repository = BaseNumbersRepository(cloudDataSource, cacheDataSource)
    }

    @Test
    fun test_all_numbers() = runBlocking {
        cacheDataSource.replaceData(
            listOf(
                NumberData("5, fact of 5"),
                NumberData("6", "fact of 6")
            )
        )

        val actual = repository.allNumbers()
        val expected = listOf(
            NumberData("5, fact of 5"),
            NumberData("6", "fact of 6")
        )

        actual.forEachIndexed { index, item ->
            assertEquals(expected[index], item)
        }
        assertEquals(1, cacheDataSource.allNumbersCallCount)
    }

    @Test
    fun test_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.changeExpected(NumberData("3", "fact of 3"))
        cacheDataSource.replaseData(emptyList())


        val actual = repository.numberFact("3")
        val expected = NumberData("3", "fact of 3")

        assertEquals(expected, actual)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(1, cloudDataSource.numberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledCount.size)

        assertEquals(1, cacheDataSource.saveNumberFactCalledCount.size)
        assertEquals(expected, cacheDataSource.data[0])


    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_number_fact_not_cached_no_connection() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaseData(emptyList())

        repository.numberFact("3")

        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(1, cloudDataSource.numberFactCalledCount)

        assertEquals(0, cacheDataSource.numberFactCalledCount)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount.size)
    }

    @Test
    fun test_number_fact_cached_success() = runBlocking {
        cloudDataSource.changeConnection(false)
        cloudDataSource.changeExpected(NumberData("3", "cloud 3"))
        cacheDataSource.replaseData(listOf(NumberData("3", "fact of 3")))

        val actual = repository.numberFact("3")
        val expected = NumberData("3", "fact of 3")

        assertEquals(expected, actual)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(0, cloudDataSource.numberFactCalledCount)

        assertEquals(1, cacheDataSource.numberFactCalledCount)
        assertEquals(cacheDataSource.numberFactCalledList[0], actual)
        assertEquals(0, cacheDataSource.saveNumberFactCalledCount.size)

    }

    @Test
    fun test_random_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.changeExpected(NumberData("3", "fact of 3"))
        cacheDataSource.replaseData(emptyList())


        val actual = repository.randomNumberFact()
        val expected = NumberData("3", "fact of 3")

        assertEquals(expected, actual)

        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cloudDataSource.numberFactCalledCount)

        assertEquals(0, cacheDataSource.numberFactCalledCount.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount.size)
        assertEquals(expected, cacheDataSource.data[0])

    }

    @Test(expected = NoInternetConnectionException::class)
    fun test_random_number_fact_not_cached_not_connected() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaseData(emptyList())

        repository.randomNumberFact()

        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)


        assertEquals(0, cacheDataSource.numberFactCalledCount)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount.size)

    }

    @Test
    fun test_random_number_fact_cached() = runBlocking {
        cloudDataSource.changeConnection(true)
        cloudDataSource.changeExpected(NumberData("3", "cloud 3"))
        cacheDataSource.replaseData(listOf(NumberData("3", "fact of 3")))

        val actual = repository.randomNumberFact("3")
        val expected = NumberData("3", "cloud 3")

        assertEquals(expected, actual)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)

        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)

        assertEquals(0, cacheDataSource.numbersFactCalledList.size)

        assertEquals(0, cacheDataSource.saveNumberFactCount)

    }

    private class TestCacheNumbersDataSource : CacheNumbersDataSource {

        var containsCalledList = mutableListOf<Boolean>()

        var numbersFactCalledList = mutableListOf<NumberData>()
        var allNumbersCallCount = 0

        var saveNumberFactCount = 0
        val saveNumberFactCalledList = mutableListOf<NumberData>()
        private val data = mutableListOf<NumberData>()


        override fun replaceData(newData: List<NumberData>) {
            data.clear()
            data.addAll(newData)
        }

        override suspend fun allNumbers(): List<NumderData> {
            allNumbersCallCount++
            return data
        }

        override fun contains(number: String): Boolean {
            val result = data.find { it.matches(number) } != null
            containsCalledList.add(result)
            return result
        }

        override suspend fun numberfact(number: String): NumberData {
            numbersFactCalledList.add(number)
            return data[0]
        }

        override suspend fun saveNumberFact(numberData: NumberData) {
            saveNumberFactCalledList.add(numberData)
            saveNumberFactCount++
            data.add(numberData)
        }

    }

    private class TestCloudDataSource : CloudDataSource {

        private var isConnection = true
        private var numberData = NuberData("", "")
        private val numberFactCalledList = mutableListOf<String>()
        private val randomNumberFactCalledList = mutableListOf<String>()
        var numberFactCalledCount = 0
        var randomNumberFactCalledCount = 0

        fun changeConnection(connected: Boolean) {
            isConnection = connected
        }

        fun changeExpected(newNumberData: NumberData) {
            numberData = newNumberData
        }

        override suspend fun numberfact(number: String): NumberData {
            return if (isConnection) {
                numberFactCalledCount++
                numberFactCalledList.add(number)
                numberData
            } else {
                throw UnknownHostException()
            }
        }

        override suspend fun randomNumberfact(number: String): NumberData {
            return if (isConnection) {
                randomNumberFactCalledCount++
                randomNumberFactCalledList.add(number)
                numberData
            } else {
                throw UnknownHostException()
            }
        }
    }


}
