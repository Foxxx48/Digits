package com.example.digits.numbers.domain

sealed class NumbersResult {

    interface Mapper<T> {
        fun map(list: List<NumberFact>): T
        fun map(errorMassage: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    data class Success(private val list: List<NumberFact> = emptyList()) : NumbersResult() {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(list)
        }
    }

    data class Failure(private val message: String) : NumbersResult() {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(message)
        }
    }

}