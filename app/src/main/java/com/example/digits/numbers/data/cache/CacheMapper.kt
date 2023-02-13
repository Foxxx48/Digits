package com.example.digits.numbers.data.cache

import com.example.digits.numbers.data.NumbersData

interface CacheMapper  {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {

        fun map(number: String, fact:String, id: Long): T

//        interface Unit<S> : Mapper<kotlin.Unit, S>
    }

}