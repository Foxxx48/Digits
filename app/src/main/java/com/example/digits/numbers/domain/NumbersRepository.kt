package com.example.digits.numbers.domain

interface NumbersRepository: RandomNumberRepository {

    suspend fun allNumbers(): List<NumberFact>

    suspend fun numberFact(number: String): NumberFact

    override suspend fun randomNumberFact(): NumberFact
}

interface RandomNumberRepository {
    suspend fun randomNumberFact(): NumberFact
}
