package com.example.digits.numbers.data.cache

import com.example.digits.numbers.data.NumberData


interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)
}

interface FetchNumber {
    suspend fun number(number: String): NumberData
}