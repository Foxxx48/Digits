package com.example.digits.numbers.data

data class NumbersData(
    val id: String,
    val fact: String
) {
    interface Mapper<T> {
        fun map(id: String, fact: String): T

        class Matches(private val id: String) : Mapper<Boolean> {
            override fun map(id: String, fact: String) = this.id == id
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, fact)
}
