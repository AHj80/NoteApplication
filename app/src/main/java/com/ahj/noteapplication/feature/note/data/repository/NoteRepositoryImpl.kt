package com.ahj.noteapplication.feature.note.data.repository

import android.util.Log
import com.ahj.noteapplication.core.common.utils.SafeHelper
import com.ahj.noteapplication.core.sessionManager.SessionManager
import com.ahj.noteapplication.feature.note.data.local.db.DAO.NoteDAO
import com.ahj.noteapplication.feature.note.data.local.remote.NoteApiService
import com.ahj.noteapplication.feature.note.data.local.remote.dto.AIRequestDto
import com.ahj.noteapplication.feature.note.data.mapper.toDomain
import com.ahj.noteapplication.feature.note.data.mapper.toDto
import com.ahj.noteapplication.feature.note.data.mapper.toEntity
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import com.ahj.noteapplication.feature.note.worker.NoteWorkerScheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDAO: NoteDAO,
    private val api: NoteApiService,
    private val safeHelper: SafeHelper,
    private val worker: NoteWorkerScheduler,
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
            api.insertNote(note.toDto(sessionManager.getUserId))
        }

        if (networkSync.isSuccess) {
            safeHelper.safeDatabase {
                noteDAO.updateSyncData(true, note.id)
            }
        } else {
            worker.schedulerSync()
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

        noteDAO.getNoteData()
            .onStart {
                if (noteDAO.getCountNote() == 0) {
                    safeHelper.safeNetwork {
                        api.getAllNote()
                    }.onSuccess { list ->
                        val entityList = list.map { it.toEntity() }
                        noteDAO.syncNote(entityList)
                    }
                }
            }.map { listEntity ->
                listEntity.map { it.toDomain() }
            }
            .catch {
                Log.e("get_note_error", it.toString())
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
                    note.toDto(sessionManager.getUserId)
                )
            }


        if (networkSync.isSuccess) {
            noteDAO.updateSyncData(true, note.id)
        } else {
            worker.schedulerSync()
        }
        return Result.success(Unit)
    }


    override suspend fun deleteNote(note: Note): Boolean {
        val roomDelete = safeHelper.safeDatabase {
            noteDAO.softDeleteNote(
                note.id
            )
        }

        if (roomDelete.isFailure) {
            return false
        }

        val networkSync = safeHelper.safeNetwork { api.deleteNote("eq.${note.id}") }

        if (networkSync.isSuccess) {
            noteDAO.hardDeleteNote(note.id)
        } else {
            worker.schedulerSync()
        }
        return true
    }

    override suspend fun unsyncedData() {

        val roomData = safeHelper.safeDatabase { noteDAO.getNoteUnsynced() }

        if (roomData.isFailure || roomData.getOrThrow().isEmpty()) return

        val noteList = roomData.getOrNull() ?: return


        val syncingData = safeHelper.safeNetwork {
            api.unsyncedData(
                noteList.map { note ->
                    note.toDto()
                }
            )
        }


        if (syncingData.isFailure) return

        safeHelper.safeDatabase {
            noteList.forEach { note ->
                noteDAO.updateSyncData(true, note.id)
            }
        }

    }

    override suspend fun textPrompt(prompt: String): Result<String> =
        safeHelper.safeAIResponse {
            api.aiRequest(AIRequestDto(prompt))
        }.map { it.result }

    override fun getExistsUnsynced(): Flow<Boolean> {
        return noteDAO.getExistsUnsynced()
    }


}
