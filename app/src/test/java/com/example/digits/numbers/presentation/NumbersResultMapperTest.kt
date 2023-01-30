package com.example.digits.numbers.presentation

import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumberUiMapper
import org.junit.Assert.*
import org.junit.Test

class NumbersResultMapperTest : BaseTest() {
    @Test
    fun test_with_empty_list() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())
        val list = emptyList<NumberFact>()

        mapper.map(list)
        assertEquals(0, communications.timeShowList)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)


    }

    @Test
    fun test_error() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        mapper.map("Error")
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("Error"), communications.stateCalledList[0])
    }

    @Test
    fun test_with_list_is_not_empty() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(communications, NumberUiMapper())

        val list = listOf(NumberFact("44", "Fact"))

        mapper.map(list)
        assertEquals(1, communications.timeShowList)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
        assertEquals(true, list[0] == NumberFact("44", "Fact"))

    }
}