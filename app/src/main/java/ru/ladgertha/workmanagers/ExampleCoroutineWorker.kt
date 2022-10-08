package ru.ladgertha.workmanagers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class ExampleCoroutineWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d(
                "ANYA",
                Arrays.deepToString(inputData.getStringArray(CatOneWorker.NAME_KEY)) ?: "No data"
            )
            Log.d("ANYA", "ExampleCoroutineWorker ${Thread.currentThread().name}")
            Thread.sleep(20000)
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val WORK_NAME = "ExampleCoroutineWorker"
        private const val NAME_KEY = "NAME_KEY"

        fun setOneTimeWork(context: Context, name: String) {
            val oneTimeRequest = OneTimeWorkRequestBuilder<ExampleCoroutineWorker>()
                // .setConstraints(getConstraints())
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData(name))
                .build()
            WorkManager.getInstance(context).enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                oneTimeRequest
            )
        }

        fun setWorkerWithChain(context: Context, name: String) {
            val oneTimeRequest = OneTimeWorkRequestBuilder<ExampleCoroutineWorker>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData(name))
                .build()

            val barsikWorker = OneTimeWorkRequestBuilder<CatTwoWorker>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData("Barsik"))
                .build()

            WorkManager.getInstance(context).beginUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                oneTimeRequest
            ).then(barsikWorker).enqueue()
        }

        fun setSeveralWorkersWithChain(context: Context, name: String) {

            val oneTimeRequest = OneTimeWorkRequestBuilder<ExampleCoroutineWorker>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData(name))
                .setInputMerger(ArrayCreatingInputMerger::class)
                .build()

            val persikWorker = OneTimeWorkRequestBuilder<CatOneWorker>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData("Persik"))
                .build()

            val barsikWorker = OneTimeWorkRequestBuilder<CatTwoWorker>()
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
                .setInputData(getData("Barsik"))
                .build()
            WorkManager.getInstance(context).beginWith(
                listOf(
                    persikWorker,
                    barsikWorker
                )
            ).then(oneTimeRequest).enqueue()
        }

        fun setPeriodicWork(context: Context, name: String) {
            val repeatingRequest =
                PeriodicWorkRequestBuilder<ExampleCoroutineWorker>(15, TimeUnit.MINUTES)
                    // .setConstraints(getConstraints())
                    .setInputData(getData(name))
                    .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
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