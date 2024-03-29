package com.example.digits

import org.junit.Test

class CheckItemReplacedTest : BaseTest() {

    @Test
    fun test_history(): Unit = NumbersPage().run {
        input.typeText("1")
        getFactButton.click()
        recycler.run {
            viewInRecycler(0, titleItem).checkText("1")
            viewInRecycler(0, subTitleItem).checkText("fact about 1")
        }

        input.typeText("2")
        getFactButton.click()
        recycler.run {
            viewInRecycler(0, titleItem).checkText("2")
            viewInRecycler(0, subTitleItem).checkText("fact about 2")
            viewInRecycler(1, titleItem).checkText("1")
            viewInRecycler(1, subTitleItem).checkText("fact about 1")
        }

        input.typeText("1")
        getFactButton.click()
        recycler.run {
            viewInRecycler(0, titleItem).checkText("1")
            viewInRecycler(0, subTitleItem).checkText("fact about 1")
            viewInRecycler(1, titleItem).checkText("2")
            viewInRecycler(1, subTitleItem).checkText("fact about 2")
        }
    }
}