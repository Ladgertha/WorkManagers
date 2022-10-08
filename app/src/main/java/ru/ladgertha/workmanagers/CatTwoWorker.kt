package ru.ladgertha.workmanagers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters

class CatTwoWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("ANYA", inputData.getString(NAME_KEY) ?: "No data")
            Log.d("ANYA", "CatTwoWorker ${Thread.currentThread().name}")
            Thread.sleep(20000)
            val outputData = Data.Builder()
                .putString(NAME_KEY, "Data from two cat")
                .build()

            Result.success(outputData)
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val NAME_KEY = "NAME_KEY"
    }
}