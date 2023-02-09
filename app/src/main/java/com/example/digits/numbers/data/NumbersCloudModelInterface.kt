package com.example.digits.numbers.data

interface NumbersCloudModelInterface {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val text: String,
        private val number: Int,
        private val found: Boolean,
        private val type: String
    ) : NumbersCloudModelInterface {

        override fun <T> map(mapper: Mapper<T>): T {
            return mapper.map(text, number, found, type)
        }
    }

    interface Mapper<T> {
        fun map(text: String, number: Int, found: Boolean, type: String): T

        class CloudModelToDomainModel(val text: String = "", val  number: Int = 0, found: Boolean = true, type: String = "") :
            Mapper<NumberData> {
            override fun map(text: String, number: Int, found: Boolean, type: String): NumberData {
                val id = number.toString()
                return NumberData(id, text)

            }
        }
    }
}