package com.example.digits.numbers.presentation

import android.widget.TextView
import androidx.core.text.isDigitsOnly

data class NumberUi(
    private val id: String,
    private val fact: String
) {
    fun map(head: TextView, subTitle: TextView) {
        head.text = id
        subTitle.text = fact
    }
}
