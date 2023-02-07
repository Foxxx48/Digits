package com.example.digits.numbers.data

interface NumbersCloudDataSource {

     suspend fun numberFact(number: String): NumberData

     suspend fun randomNumberFact(number: String): NumberData
}
