package ru.ladgertha.workmanagers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ExampleCoroutineWorker.setPeriodicWork("Anna")
        ExampleWorker.setPeriodicWork("Kristina")
        ExampleListenableWorker.setPeriodicWork("Daniil")
    }
}