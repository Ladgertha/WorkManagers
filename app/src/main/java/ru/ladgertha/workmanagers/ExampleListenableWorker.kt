package ru.ladgertha.workmanagers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.TimeUnit

class ExampleListenableWorker(context: Context, params: WorkerParameters) :
    ListenableWorker(context, params) {

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->
            Log.d("TEST", inputData.getString(NAME_KEY) ?: "No data")
            Log.d("TEST", "ExampleListenableWorker ${Thread.currentThread().name}")
            Thread.sleep(20000)
            completer.set(Result.success())
        }
    }

    override fun onStopped() {
        super.onStopped()
        // close resources
    }

    companion object {
        private const val WORK_NAME = "ExampleListenableWorker"
        private const val NAME_KEY = "NAME_KEY"

        fun setOneTimeWork(name: String) {
            val oneTimeRequest = OneTimeWorkRequestBuilder<ExampleListenableWorker>()
               // .setConstraints(getConstraints())
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData(name))
                .build()
            WorkManager.getInstance().enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                oneTimeRequest
            )
        }

        fun setPeriodicWork(name: String) {
            val repeatingRequest =
                PeriodicWorkRequestBuilder<ExampleListenableWorker>(15, TimeUnit.MINUTES)
                   // .setConstraints(getConstraints())
                    .setInputData(getData(name))
                    .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                repeatingRequest
            )
        }

        private fun getConstraints() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        private fun getData(name: String) = Data.Builder().apply {
            putString(NAME_KEY, name)
        }.build()
    }
}