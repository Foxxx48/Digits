package com.example.digits.numbers.data.cloud

import com.example.digits.numbers.data.NumbersData
import com.example.digits.numbers.presentation.Mapper

data class NumbersCloudModel(
    val text: String = "",
    val number: Int = 0,
    val found: Boolean = true,
    val type: String = ""
) : Mapper<NumbersData, NumbersCloudModel>{

    override fun map(source: NumbersCloudModel): NumbersData {
        val id = source.number.toString()
        return NumbersData(id, source.text )
    }


}
