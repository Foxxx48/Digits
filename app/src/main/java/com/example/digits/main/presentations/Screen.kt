package com.example.digits.main.presentations

import com.example.digits.details.presentation.NumberDetailsFragment
import com.example.digits.numbers.presentation.NumbersFragment

sealed class Screen {

    abstract fun fragment(): Class<out BaseFragment<*>>

    object Details : Screen() {
        override fun fragment(): Class<out BaseFragment<*>> = NumberDetailsFragment::class.java
    }

    object Numbers : Screen() {
        override fun fragment(): Class<out BaseFragment<*>> = NumbersFragment::class.java
    }
}