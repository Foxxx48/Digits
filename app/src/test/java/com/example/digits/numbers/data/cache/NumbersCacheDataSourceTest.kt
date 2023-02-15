package com.example.digits.numbers.data.cache

import com.example.digits.numbers.data.NumbersData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumbersCacheDataSourceTest {

    private lateinit var dao: TestDao
    private lateinit var dataSource: NumbersCacheDataSource

    @Before
    fun setUp() {
        dao = TestDao()
        dataSource = NumbersCacheDataSource.Base(dao, TestMapper(5))
    }

    @Test
    fun test_all_numbers_empty() = runBlocking {
        val actual = dataSource.allNumbers()
        val expected = emptyList<NumbersCache>()

        assertEquals(expected, actual)
    }

    @Test
    fun test_all_numbers_not_empty() = runBlocking {
        dao.data.add(NumbersCache("1", "fact1", 1))
        dao.data.add(NumbersCache("2", "fact2", 2))

        val actualList = dataSource.allNumbers()
        val expectedList = listOf(
            NumbersData("1", "fact1"),
            NumbersData("2", "fact2")
        )
        actualList.forEachIndexed { index, actual ->
            assertEquals(expectedList[index], actual)
        }
    }

    @Test
    fun test_contains() = runBlocking {
        dao.data.add(NumbersCache("6", "fact6", 6))

        val actual = dataSource.contains("6")
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun test_not_contain() = runBlocking {
        dao.data.add(NumbersCache("7", "fact7", 7))

        val actual = dataSource.contains("6")
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun test_save() = runBlocking {
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(8))

        dataSource.saveNumber(NumbersData("8", "fact8"))
        assertEquals(NumbersCache("8", "fact8", 8), dao.data[0])
    }

    @Test
    fun test_number_contains() = runBlocking {
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(9))

        dao.data.add(NumbersCache("10", "fact10", 10))

        val actual = dataSource.number("10")
        val expected = NumbersData("10", "fact10")
        assertEquals(expected, actual)
    }

    @Test
    fun test_number_doesnt_exist() = runBlocking {
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(9))

        dao.data.add(NumbersCache("10", "fact10", 10))

        val actual = dataSource.number("32")
        val expected = NumbersData("", "")
        assertEquals(expected, actual)
    }
}

private class TestDao : NumbersDao {

    val data = mutableListOf<NumbersCache>()

    override fun allNumbers(): List<NumbersCache> = data

    override fun insert(number: NumbersCache) {
        data.add(number)
    }

    override fun number(number: String): NumbersCache? =
        data.find { it.number == number }
}

private class TestMapper(private val date: Long) : NumbersData.Mapper<NumbersCache> {
    override fun map(id: String, fact: String) = NumbersCache(id, fact, date)

}

