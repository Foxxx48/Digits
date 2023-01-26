package com.example.digits.numbers.domain

sealed class NumbersResult {

    interface Mapper<T> {
        fun map(list: List<NumberFact>, errorMassage: String): T
    }

    abstract fun <T> map( mapper: Mapper<T>): T

    class Success(private val list: List<NumberFact>) : NumbersResult() {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(list, "")
        }
    }

    class Failure(private val message: String) : NumbersResult() {
        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(emptyList(), message)
        }
    }

}