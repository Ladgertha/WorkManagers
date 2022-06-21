//package ru.ladgertha.workmanagers
//
//import android.content.Context
//import android.os.Build
//import android.util.Log
//import androidx.work.*
//import io.reactivex.Single
//import java.util.concurrent.TimeUnit
//
//class ExampleRxWorker(context: Context, params: WorkerParameters) :
//    RxWorker(context, params) {
//
//    override fun createWork(): Single<Result> {
//        return Single.create {
//            try {
//                Log.d("TEST", inputData.getString(NAME_KEY) ?: "No data")
//                Result.success()
//            } catch (exception: Exception) {
//                Result.failure()
//            }
//        }
//    }
//
//    override fun onStopped() {
//        super.onStopped()
//        // close resources
//    }
//
//    companion object {
//        private const val WORK_NAME = "ExampleWorker"
//        private const val NAME_KEY = "NAME_KEY"
//
//        fun setOneTimeWork(name: String) {
//            val oneTimeRequest = OneTimeWorkRequestBuilder<ExampleWorker>()
//                // .setConstraints(getConstraints())
//                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES)
//                .setInputData(getData(name))
//                .build()
//            WorkManager.getInstance().enqueueUniqueWork(
//                WORK_NAME,
//                ExistingWorkPolicy.REPLACE,
//                oneTimeRequest
//            )
//        }
//
//        fun setPeriodicWork(name: String) {
//            val repeatingRequest =
//                PeriodicWorkRequestBuilder<ExampleWorker>(15, TimeUnit.MINUTES)
//                    //.setConstraints(getConstraints())
//                    .setInputData(getData(name))
//                    .build()
//
//            WorkManager.getInstance().enqueueUniquePeriodicWork(
//                WORK_NAME,
//                ExistingPeriodicWorkPolicy.REPLACE,
//                repeatingRequest
//            )
//        }
//
//        private fun getConstraints() = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.UNMETERED)
//            .setRequiresBatteryNotLow(true)
//            .setRequiresCharging(true)
//            .apply {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setRequiresDeviceIdle(true)
//                }
//            }.build()
//
//        private fun getData(name: String) = Data.Builder().apply {
//            putString(NAME_KEY, name)
//        }.build()
//    }
//}