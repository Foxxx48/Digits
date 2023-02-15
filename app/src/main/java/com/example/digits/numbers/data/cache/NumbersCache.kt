package com.example.digits.numbers.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.digits.numbers.data.NumbersData
import com.example.digits.numbers.presentation.Mapper

@Entity("numbers_table")
data class NumbersCache(

    @PrimaryKey
    @ColumnInfo("number")
    val number: String,

    @ColumnInfo("fact")
    val fact: String,

    @ColumnInfo("date")
    val date: Long
)
//    : Mapper<NumbersData, NumbersCache> {
//
//    override fun map(source: NumbersCache): NumbersData {
//        return NumbersData(number,fact)
//    }
//
//}
