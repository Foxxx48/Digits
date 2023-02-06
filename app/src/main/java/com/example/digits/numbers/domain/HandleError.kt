package com.example.digits.numbers.domain

import com.example.digits.R
import com.example.digits.numbers.presentation.ManageResources

interface HandleError<T> {
    fun handle(e: Exception): T

    class Base(private val manageResources: ManageResources) : HandleError<String> {

        override fun handle(e: Exception) = manageResources.string(
            when (e) {
                is NoInternetConnectionException -> R.string.no_connection_message
                else -> R.string.service_is_unavailable
            }
        )
    }
}