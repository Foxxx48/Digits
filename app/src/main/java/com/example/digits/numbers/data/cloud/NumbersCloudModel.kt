package com.example.digits.numbers.data.cloud

import com.example.digits.numbers.data.NumberData
import com.example.digits.numbers.presentation.Mapper

data class NumbersCloudModel(
    val text: String = "",
    val number: Int = 0,
    val found: Boolean = true,
    val type: String = ""
) : Mapper<NumberData, NumbersCloudModel>{

    override fun map(source: NumbersCloudModel): NumberData {
        val id = source.number.toString()
        return NumberData(id, source.text )
    }


}
