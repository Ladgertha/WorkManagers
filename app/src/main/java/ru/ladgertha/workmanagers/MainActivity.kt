package ru.ladgertha.workmanagers

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.startWorkers).setOnClickListener {
          //  ExampleCoroutineWorker.setPeriodicWork("ExampleCoroutineWorker: name from periodic work")
            ExampleCoroutineWorker.setWorkerWithChain(this,"ExampleCoroutineWorker")
         //   ExampleWorker.setPeriodicWork("ExampleWorker: name from periodic work")
         //   ExampleWorker.setOneTimeWork("ExampleWorker: name from one time work")
         //   ExampleListenableWorker.setPeriodicWork("ExampleListenableWorker: name from periodic work")
          //  ExampleListenableWorker.setOneTimeWork("ExampleListenableWorker: name from one time work")
            // ExampleRxWorker.setPeriodicWork("ExampleRxWorker: name from periodic work")
            // ExampleRxWorker.setOneTimeWork("ExampleRxWorker: name from one time work")
        }
    }
}