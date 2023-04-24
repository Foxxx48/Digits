package com.example.digits

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.digits.main.presentations.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NavigationTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test

    fun detail_navigation(){
        onView(withId(R.id.et_textfield)).perform(typeText("10"))
        onView(withId(R.id.btn_get_fact)).perform(click())

        onView(withId(R.id.tv_title)).check(matches(withText("10")))
        onView(withId(R.id.tv_subTitle)).check(matches(withText("fact about 10")))

        onView(withId(R.id.tv_subTitle)).perform(click())
        onView(withId(R.id.tv_Details)).check(matches(withText("10\n\nfact about 10")))
    }

}