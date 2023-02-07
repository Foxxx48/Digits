package com.example.digits.numbers.data

import com.example.digits.numbers.domain.HandleError
import com.example.digits.numbers.domain.NoInternetConnectionException
import com.example.digits.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {
    override fun handle(e: Exception): Exception {
        return when(e) {
            is UnknownHostException -> NoInternetConnectionException()

            else -> ServiceUnavailableException()
        }

    }
}