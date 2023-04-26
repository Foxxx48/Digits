package com.example.digits.numbers.sl

import android.telecom.Call.Details
import com.example.digits.main.sl.Core
import com.example.digits.main.sl.Module
import com.example.digits.numbers.data.BaseNumbersRepository
import com.example.digits.numbers.data.HandleDataRequest
import com.example.digits.numbers.data.HandleDomainError
import com.example.digits.numbers.data.NumberDataToDomain
import com.example.digits.numbers.data.cache.DataModelToCacheModel
import com.example.digits.numbers.data.cache.NumbersCacheDataSource
import com.example.digits.numbers.data.cloud.NumbersCloudDataSource
import com.example.digits.numbers.data.cloud.NumbersService
import com.example.digits.numbers.domain.HandleError
import com.example.digits.numbers.domain.HandleRequest
import com.example.digits.numbers.domain.NumberUiMapper
import com.example.digits.numbers.domain.NumbersInteractor
import com.example.digits.numbers.presentation.DetailsUi
import com.example.digits.numbers.presentation.HandleNumbersRequest
import com.example.digits.numbers.presentation.NumbersCommunications
import com.example.digits.numbers.presentation.NumbersListCommunication
import com.example.digits.numbers.presentation.NumbersResultMapper
import com.example.digits.numbers.presentation.NumbersStateCommunications
import com.example.digits.numbers.presentation.NumbersViewModel
import com.example.digits.numbers.presentation.ProgressCommunication

class NumbersModule(private val core: Core) : Module<NumbersViewModel.Base> {
    override fun viewModel(): NumbersViewModel.Base {
        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            NumbersStateCommunications.Base(),
            NumbersListCommunication.Base()
        )
        val cacheDataSource = NumbersCacheDataSource.Base(
            core.provideDatabase().numbersDao(),
            DataModelToCacheModel()
        )
        val repository = BaseNumbersRepository(
            NumbersCloudDataSource.Base(
                core.service(NumbersService::class.java)
            ),
            cacheDataSource,
            HandleDataRequest.Base(
                cacheDataSource,
                NumberDataToDomain(),
                HandleDomainError()
            ),
            NumberDataToDomain()
        )
        return NumbersViewModel.Base(
            HandleNumbersRequest.Base(
                core.provideDispatchers(),
                communications,
                NumbersResultMapper(communications, NumberUiMapper())
            ),
            core,
            communications,
            NumbersInteractor.Base(
                repository,
                HandleRequest.Base(
                    HandleError.Base(core),
                    repository
                )
            ),
            core.provideNavigation(),
            DetailsUi()
        )
    }
}