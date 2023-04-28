package com.example.digits.numbers.data.cloud

import retrofit2.Response

class MockNumbersService (
    private val randomApiHeader: RandomApiHeader.MockResponse
) : NumbersService {

    //    private var count = 0
//
//    override suspend fun randomFact(): Response<NumbersCloudModel> =
//    }
//
//    override suspend fun fact(id: String): NumbersCloudModel = "fact about $id"
    override suspend fun fact(id: String): NumbersCloudModel {
        TODO("Not yet implemented")
    }

    override suspend fun randomFact(): Response<NumbersCloudModel> {
        TODO("Not yet implemented")
    }
}