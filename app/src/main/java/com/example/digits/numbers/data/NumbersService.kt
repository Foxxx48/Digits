package com.example.digits.numbers.data

import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersService {

    @GET("{id}/?json")
    suspend fun fact(@Path("id") id: String): NumbersCloudModel

    @GET("random/math/?json")
    suspend fun randomFact(): NumbersCloudModel

}