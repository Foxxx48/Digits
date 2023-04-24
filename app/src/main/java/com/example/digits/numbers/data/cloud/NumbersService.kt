package com.example.digits.numbers.data.cloud

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersService {
// with using the NumbersCloudModelInterface.Base
//    @GET("{id}/?json")
//    suspend fun fact(@Path("id") id: String): NumbersCloudModelInterface.Base
//
//    @GET("random/math/?json")
//    suspend fun randomFact(): NumbersCloudModelInterface.Base

    @GET("{id}/?json")
    suspend fun fact(@Path("id") id: String): NumbersCloudModel

    @GET("random/math/?json")
    suspend fun randomFact(): Response<NumbersCloudModel>

}