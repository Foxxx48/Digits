package com.example.digits.numbers.data

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
    suspend fun randomFact(): NumbersCloudModel

}