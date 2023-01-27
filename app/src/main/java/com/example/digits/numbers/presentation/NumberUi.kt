package com.example.digits.numbers.presentation

import android.widget.TextView
import androidx.core.text.isDigitsOnly

data class NumberUi(
    private val id: String,
    private val fact: String
) : Mapper<Boolean, NumberUi>{
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, fact)

    interface Mapper<T> {
        fun map(id: String, fact: String): T
    }

    override fun map(source: NumberUi): Boolean  = source.id == id
}
