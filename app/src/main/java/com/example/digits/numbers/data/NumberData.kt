package com.example.digits.numbers.data

data class NumberData(
    val id: String,
    val fact: String
) {
    interface Mapper<T> {
        fun map(id: String, fact: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, fact)
}
