package com.example.digits.numbers.data

interface NumbersCloudDataSource : FetchNumber {
     suspend fun randomNumber(): NumberData
}
