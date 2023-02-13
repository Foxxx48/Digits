package com.example.digits.numbers.data.cache

import com.example.digits.numbers.data.NumbersData

class DataModelToCacheModel() : NumbersData.Mapper<NumbersCache> {
    override fun map(id: String, fact: String): NumbersCache {
        return NumbersCache(id, fact, System.currentTimeMillis())
    }

}