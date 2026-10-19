package com.ahj.noteapplication.feature.note.domain.repository

import com.ahj.noteapplication.feature.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface RepositoryNote {

    suspend fun addNote(note: Note): Result<Unit>

    fun getNoteData(): Flow<List<Note>>

    suspend fun getNoteById(id: String) : Note?

    suspend fun deleteNote(note: Note): Boolean

    suspend fun updateData(
        note: Note
    ): Result<Unit>

    suspend fun unsyncedData()

    suspend fun syncNoteData(note: List<Note>): Result<Unit>

    suspend fun textPrompt(prompt: String): Result<String>

    fun getExistsUnsynced(): Flow<Boolean>


}