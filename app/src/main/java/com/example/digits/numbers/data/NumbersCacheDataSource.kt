package com.example.digits.numbers.data

interface NumbersCacheDataSource {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun numberFact(number: String): NumberData

    suspend fun saveNumberFact(numberData: NumberData)
}