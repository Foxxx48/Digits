package com.example.digits.numbers.data.cache

import com.example.digits.numbers.data.NumbersData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumbersData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numbersData: NumbersData)

    class Base(
        private val numbersDao: NumbersDao,
        private val cacheMapper: NumbersData.Mapper<NumbersCache>
    ) : NumbersCacheDataSource {

        private val mutex = Mutex()
        override suspend fun allNumbers(): List<NumbersData> = mutex.withLock {
            val numbers = numbersDao.allNumbers()
            return numbers.map { NumbersData(it.number, it.fact) }
//            return numbers.map { it.map(it)  }
        }

        override suspend fun contains(number: String): Boolean = mutex.withLock {
            val numberCache = numbersDao.number(number)
            return numberCache != null
        }

        override suspend fun saveNumber(numbersData: NumbersData) = mutex.withLock {
            numbersDao.saveNumber(numbersData.map(cacheMapper))
        }

        override suspend fun number(number: String): NumbersData = mutex.withLock {
            val numberCache = numbersDao.number(number) ?: NumbersCache("", "", 0)
            return NumbersData(numberCache.number, numberCache.fact)
        }
    }
}

interface FetchNumber {
    suspend fun number(number: String): NumbersData
}