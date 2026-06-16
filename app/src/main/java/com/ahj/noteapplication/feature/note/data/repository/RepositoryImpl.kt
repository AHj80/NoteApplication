package com.ahj.noteapplication.feature.note.data.repository

import android.util.Log
import com.ahj.noteapplication.core.common.utils.persiandate.PersianDate
import com.ahj.noteapplication.feature.note.data.local.DAO.NoteDAO
import com.ahj.noteapplication.feature.note.data.mapper.toDomain
import com.ahj.noteapplication.feature.note.data.mapper.toEntity
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val noteDAO: NoteDAO,
    private val persianDate: PersianDate,
) : RepositoryNote {



    override suspend fun addNote(note: Note): Result<Long> {
        return try {
            val result = noteDAO.addNote(note.toEntity())
            Result.success(result)
        } catch (e: Exception) {
            Log.e("Add_Note_Error", e.message.toString())
            Result.failure(e)
        }
    }


    override fun getNoteData(): Flow<List<Note>> {

        val data = noteDAO.getNoteData()
        return data.map { listData->
            listData.map {
                it.toDomain()
            }
        }
            .catch { e ->
                Log.e("ERROR GETTING DATA" , e.message.toString())
                throw e
            }

    }

    override suspend fun getNoteById(id: Int) : Note? {
        return noteDAO.getNoteDataById(id)?.toDomain()

    }


    override suspend fun deleteNote(note: Note): Boolean {
        return try {
            noteDAO.deleteNote(note.toEntity())
            true
        } catch (e: Exception) {
            Log.e("DELETE_ERROR", e.message.toString())
            false
        }

    }

    override suspend fun updateData(
        id: Int,
        title: String,
        body: String,
        fontSize: Int,
        date: String
    ): Boolean {
        return try {
            val note = Note(id = id, title = title, body = body, fontSize = fontSize, date = date)
            noteDAO.updateNote(note.toEntity())
            true
        } catch (e: Exception) {
            Log.e("TAG_Error Update", e.message.toString())
            false
        }
    }

    override fun showDate(): String =
        "${persianDate.year} - ${persianDate.month} - ${persianDate.day} | ${persianDate.hour}:${persianDate.min} "

}