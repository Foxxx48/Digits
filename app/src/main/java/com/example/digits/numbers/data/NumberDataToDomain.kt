package com.example.digits.numbers.data

import com.example.digits.numbers.domain.NumberFact

class NumberDataToDomain: NumbersData.Mapper<NumberFact> {
    override fun map(id: String, fact: String): NumberFact {
        return NumberFact(id, fact)
    }
}