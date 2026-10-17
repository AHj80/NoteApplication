package com.ahj.noteapplication.feature.note.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class SyncNoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams : WorkerParameters,
    private val repository: RepositoryNote
): CoroutineWorker(context , workerParams){
    override suspend fun doWork(): Result {

        return try {
            repository.unsyncedData()
            Result.success()
        }catch (e: Exception){
            Log.i("ahj-worker" , e.message.toString())
            if (runAttemptCount<3)
                Result.retry()
            else {
                Result.failure()
            }
        }

    }
}