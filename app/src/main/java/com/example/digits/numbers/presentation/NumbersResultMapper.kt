package com.example.digits.numbers.presentation

import com.example.digits.numbers.domain.NumberFact
import com.example.digits.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumbersCommunications,
    private val mapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMassage: String) {
        communications.showState(
            if (errorMassage.isEmpty()) {
                val numberList = list.map { it.map(mapper) }
                if (numberList.isNotEmpty())
                    communications.showList(list.map { it.map(mapper) })
                UiState.Success()
            } else {
                UiState.Error(errorMassage)
            }
        )
    }
}