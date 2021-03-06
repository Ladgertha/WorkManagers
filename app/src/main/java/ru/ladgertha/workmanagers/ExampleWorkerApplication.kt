package ru.ladgertha.workmanagers

import android.app.Application
import androidx.work.Configuration

class ExampleWorkerApplication : Application(), Configuration.Provider {

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}