package com.ahj.noteapplication.feature.note.data.repository

import android.util.Log
import com.ahj.noteapplication.core.common.utils.SafeHelper
import com.ahj.noteapplication.core.sessionManager.SessionManager
import com.ahj.noteapplication.feature.note.data.local.db.DAO.NoteDAO
import com.ahj.noteapplication.feature.note.data.local.remote.NoteApiService
import com.ahj.noteapplication.feature.note.data.mapper.toDomain
import com.ahj.noteapplication.feature.note.data.mapper.toDto
import com.ahj.noteapplication.feature.note.data.mapper.toEntity
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDAO: NoteDAO,
    private val api: NoteApiService,
    private val safeHelper: SafeHelper,
    private val sessionManager: SessionManager
) : RepositoryNote {


    override suspend fun addNote(note: Note): Result<Unit> {

        val insertRoom = safeHelper.safeDatabase {
            noteDAO.upsertNote(
                note.toEntity(false, sessionManager.getUserId)
            )
        }

        if (insertRoom.isFailure) {
            return insertRoom
        }

        val networkSync = safeHelper.safeNetwork {
            api.insertNote(note.toDto("7fdcf63c-a148-4e95-87b0-197fc5808637"))
        }

        if (networkSync.isFailure){
            Log.i("ssss" , networkSync.exceptionOrNull().toString())
        }
        if (networkSync.isSuccess) {
            safeHelper.safeDatabase {
                noteDAO.updateSyncData(true, note.id)
            }
        }


        return Result.success(Unit)

    }

    override suspend fun syncNoteData(note: List<Note>): Result<Unit> {
        val network = safeHelper.safeNetwork {
            api.getAllNote()
        }.map {
            it.map { note ->
                note.toEntity()
            }
        }
        if (network.isSuccess) {
            noteDAO.syncNote(network.getOrThrow())
        }

        return Result.success(Unit)
    }

    override fun getNoteData(): Flow<List<Note>> =
        noteDAO.getNoteData().map { listNote ->
            listNote.map { noteEntity ->
                noteEntity.toDomain()
            }
        }.catch { e ->
            Log.e("ERROR", "${e.message}")
        }

    override suspend fun getNoteById(id: String): Note? {
        return noteDAO.getNoteDataById(id)?.toDomain()
    }

    override suspend fun updateData(note: Note): Result<Unit> {

        val roomUpdate = safeHelper.safeDatabase {
            noteDAO.upsertNote(
                note.toEntity(
                    false,
                    sessionManager.getUserId
                )
            )
        }

        if (roomUpdate.isFailure) {
            return roomUpdate
        }

        val networkSync =
            safeHelper.safeNetwork {
                api.insertNote(
                    note.toDto("7fdcf63c-a148-4e95-87b0-197fc5808637")
                )
            }


        if (networkSync.isSuccess) {
            noteDAO.updateSyncData(true, note.id)
        }
        return Result.success(Unit)
    }

    override suspend fun deleteNote(note: Note): Boolean {

        val roomDelete = safeHelper.safeDatabase {
            noteDAO.deleteNote(
                note.toEntity(
                    false, sessionManager.getUserId
                )
            )
        }

        if (roomDelete.isFailure) {
            return false
        }

        val networkSync = safeHelper.safeNetwork { api.deleteNote("eq.${note.id}") }

        if (networkSync.isFailure){
            Log.i("ssss" , networkSync.exceptionOrNull().toString())
        }

        if (networkSync.isSuccess) {
            noteDAO.updateSyncData(true, note.id)
        }
        return true
    }

}