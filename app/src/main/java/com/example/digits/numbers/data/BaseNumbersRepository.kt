package com.example.digits.numbers.data

import com.example.digits.numbers.domain.HandleError
import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumbersRepository


class BaseNumbersRepository(
    private val cloudDataSource: NumbersCloudDataSource,
    private val cacheDataSource: NumbersCacheDataSource,
    private val mapperToDomain: NumberData.Mapper<NumberFact>,
    private val handleError: HandleError<Exception>
) : NumbersRepository {
    override suspend fun allNumbers(): List<NumberFact> {
        val data = cacheDataSource.allNumbers()
        return data.map { it.map(mapperToDomain) }
    }

    override suspend fun numberFact(number: String): NumberFact {
        return if (cacheDataSource.contains(number)) {
            val numberData = cacheDataSource.number(number)
            cacheDataSource.saveNumberFact(numberData)
            numberData.map(mapperToDomain)

        } else {
            try {
                val result = cloudDataSource.number(number)
                cacheDataSource.saveNumberFact(result)
                result.map(mapperToDomain)
            } catch (e: Exception) {
                throw handleError.handle(e)
            }
        }
    }

    override suspend fun randomNumberFact(): NumberFact {
        return try {
            val number = cloudDataSource.randomNumber()
            cacheDataSource.saveNumberFact(number)
            number.map(mapperToDomain)
        } catch (e: Exception) {
            throw handleError.handle(e)
        }
    }
}