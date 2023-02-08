package com.example.digits.numbers.data

interface NumbersCloudDataSource : FetchNumber {
     suspend fun randomNumber(): NumberData

     class Base(private val numbersService: NumbersService,
     ) : NumbersCloudDataSource {
          override suspend fun randomNumber(): NumberData {
               val randomFact = numbersService.randomFact()
               return randomFact.map(randomFact)
          }

          override suspend fun number(number: String): NumberData {
               val fact = numbersService.fact(number)
               return fact.map(fact)
          }

     }
}
