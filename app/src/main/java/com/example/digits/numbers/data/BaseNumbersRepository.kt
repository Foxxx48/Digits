package com.example.digits.numbers.data

import com.example.digits.numbers.data.cache.NumbersCacheDataSource
import com.example.digits.numbers.data.cloud.NumbersCloudDataSource
import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumbersRepository


class BaseNumbersRepository(
    private val cloudDataSource: NumbersCloudDataSource,
    private val cacheDataSource: NumbersCacheDataSource,
    private val handleDataRequest: HandleDataRequest,
    private val mapperToDomain: NumbersData.Mapper<NumberFact>
    ) : NumbersRepository {
    override suspend fun allNumbers(): List<NumberFact> {
        val data = cacheDataSource.allNumbers()
        return data.map { it.map(mapperToDomain) }
    }

    override suspend fun numberFact(number: String) = handleDataRequest.handle {
        val dataSource = if (cacheDataSource.contains(number))
            cacheDataSource
        else
            cloudDataSource
        dataSource.number(number)
    }

    override suspend fun randomNumberFact() = handleDataRequest.handle {
        cloudDataSource.randomNumber()
    }
}