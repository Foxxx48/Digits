package com.example.digits.numbers.presentation

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

sealed class UiState {
    abstract fun apply(inputLayout: TextInputLayout, inputEditText: TextInputEditText)
    class Success() : UiState() {
        override fun apply(inputLayout: TextInputLayout, inputEditText: TextInputEditText) {
            inputEditText.setText("")
        }
    }

    data class ShowError(private val message: String) : AbstractError(message, true)
    class ClearError() : AbstractError("", false)
    abstract class AbstractError(private val message: String, private val errorEnabled: Boolean) :
        UiState() {
        override fun apply(inputLayout: TextInputLayout, inputEditText: TextInputEditText) {
            inputLayout.isErrorEnabled = errorEnabled
            inputLayout.error = message
        }

    }
}