package com.example.digits.numbers.data.cloud

import retrofit2.Response

class MockNumbersService : NumbersService {

    override suspend fun fact(id: String): NumbersCloudModel = NumbersCloudModel("fact about 10", 10)

    override suspend fun randomFact(): Response<NumbersCloudModel> {
        TODO("Not yet implemented")
    }


}