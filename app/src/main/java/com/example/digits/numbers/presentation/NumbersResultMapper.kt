package com.example.digits.numbers.presentation

import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumbersCommunications,
    private val mapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>) {
        if (list.isNotEmpty())
            communications.showList(list.map { it.map(mapper) })
        communications.showState(UiState.Success())
    }

    override fun map(errorMassage: String) {
        communications.showState(UiState.Error(errorMassage))
    }
}