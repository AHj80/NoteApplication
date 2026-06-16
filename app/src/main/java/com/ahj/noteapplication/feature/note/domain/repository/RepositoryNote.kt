package com.ahj.noteapplication.feature.note.domain.repository

import com.ahj.noteapplication.feature.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface RepositoryNote {

    suspend fun addNote(note: Note): Result<Long>

    fun getNoteData(): Flow<List<Note>>

    suspend fun getNoteById(id: Int) : Note?

    suspend fun deleteNote(note: Note): Boolean

    suspend fun updateData(
        id: Int,
        title: String,
        body: String,
        fontSize: Int,
        date: String
    ): Boolean

    fun showDate(): String


}