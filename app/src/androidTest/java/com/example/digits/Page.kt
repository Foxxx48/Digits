package com.example.digits

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers

abstract class Page {
    protected fun Int.view() = Espresso.onView(ViewMatchers.withId(this))
}