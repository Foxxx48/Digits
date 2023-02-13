package com.example.digits.numbers.data.cloud

import com.example.digits.numbers.data.NumbersData
import com.example.digits.numbers.data.cache.FetchNumber

interface NumbersCloudDataSource : FetchNumber {
     suspend fun randomNumber(): NumbersData

     class Base(private val numbersService: NumbersService) : NumbersCloudDataSource {
          override suspend fun randomNumber(): NumbersData {
               val randomFact = numbersService.randomFact()
               return randomFact.map(randomFact)
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
