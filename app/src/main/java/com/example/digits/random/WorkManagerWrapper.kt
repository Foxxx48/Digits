package com.example.digits.random

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

interface WorkManagerWrapper {

    fun start()

    class Base(context: Context) : WorkManagerWrapper {

        private val workManager = WorkManager.getInstance(context)

        override fun start() {
            val request = PeriodicWorkRequestBuilder<PeriodicRandomWorker>(
                15,
                TimeUnit.MINUTES
            ).setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        companion object {
            private const val WORK_NAME = "random number work"
        }
    }
}