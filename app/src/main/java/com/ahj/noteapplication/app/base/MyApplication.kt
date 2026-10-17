package com.ahj.noteapplication.app.base

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.ahj.noteapplication.feature.note.worker.NoteWorkerScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workerScheduler: NoteWorkerScheduler

    override fun onCreate() {
        super.onCreate()

        workerScheduler.schedulerSyncPeriodic()
    }

    override val workManagerConfiguration: Configuration
        get()  {
            Log.d("WorkerDebug", "Custom WorkManager configuration")
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        }


}