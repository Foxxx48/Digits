package com.example.digits.numbers.data.cloud

import com.example.digits.numbers.data.NumbersData
import com.example.digits.numbers.data.cache.FetchNumber

interface NumbersCloudDataSource : FetchNumber {
    suspend fun randomNumber(): NumbersData

    class Base(
        private val numbersService: NumbersService,
        private val randomApiHeader: RandomApiHeader
    ) : NumbersCloudDataSource {
        override suspend fun randomNumber(): NumbersData {
            val response = numbersService.randomFact()
            val body = response.body() ?: throw IllegalStateException("service unavailable")
            val headers = response.headers()
            randomApiHeader.findInHeaders(headers)?.let { (_, value) ->

                return body.map(body)
            }
            throw IllegalStateException("service unavailable")
// with using the NumbersCloudModelInterface.Base
//               return randomFact.map(NumbersCloudModelInterface.Mapper.CloudModelToDomainModel())
        }

        override suspend fun number(number: String): NumbersData {
            val fact = numbersService.fact(number)
            return fact.map(fact)
// with using the NumbersCloudModelInterface.Base
//               return fact.map(NumbersCloudModelInterface.Mapper.CloudModelToDomainModel())
        }
    }
}
