package com.example.digits.numbers.data

import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumbersRepository


class BaseNumbersRepository(
    private val cloudDataSource: NumbersCloudDataSource,
    private val cacheDataSource: NumbersCacheDataSource
) : NumbersRepository {
    override suspend fun allNumbers(): List<NumberFact> {
        TODO("Not yet implemented")
    }

    override suspend fun numberFact(number: String): NumberFact {
        TODO("Not yet implemented")
    }

    override suspend fun randomNumberFact(): NumberFact {
        TODO("Not yet implemented")
    }
}