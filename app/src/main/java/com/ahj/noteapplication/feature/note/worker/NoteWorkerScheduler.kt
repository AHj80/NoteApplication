package com.ahj.noteapplication.feature.note.worker

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NoteWorkerScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun schedulerSync() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request =
            OneTimeWorkRequestBuilder<SyncNoteWorker>()
                .setConstraints(constraint)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    30,
                    TimeUnit.SECONDS
                )
                .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "note-sync",
                ExistingWorkPolicy.KEEP,
                request
            )

        Log.i("ahj-worker" , "success")
    }

    fun schedulerSyncPeriodic() {

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncNoteWorker>(
            12, TimeUnit.HOURS
        )
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "periodic-sync-work",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )


    }

}