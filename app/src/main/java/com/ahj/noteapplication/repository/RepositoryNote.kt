package com.ahj.noteapplication.repository

import android.util.Log
import com.ahj.noteapplication.data.local.db.DAO.NoteDAO
import com.ahj.noteapplication.data.local.db.entitys.NoteEntity
import ir.amozeshgam.persiandate.PersianDate
import javax.inject.Inject


class RepositoryNote @Inject constructor(
    private val noteDAO: NoteDAO,
    private val persianDate: PersianDate,
) {

    val date =
        "${persianDate.year} - ${persianDate.month} - ${persianDate.day} | ${persianDate.hour}:${persianDate.min} "


    suspend fun addNote(noteEntity: NoteEntity): Result<Long> =
        try {
            val result = noteDAO.addNote(noteEntity)
            Result.success(result)
        } catch (e: Exception) {
            Log.e("Note_ERROR", e.message.toString())
            Result.failure(e)
        }

    fun getNoteData() = noteDAO.getNoteData()

    suspend fun getNoteDataById(id: Int) = noteDAO.getNoteDataById(id)

    suspend fun deleteNote(note: NoteEntity): Boolean =
        try {
            noteDAO.deleteNote(note)
            true
        } catch (e: Exception) {
            Log.e("TAG_DeleteNote", e.message.toString())
            false
        }


    suspend fun updateNoteData(id: Int, title: String, body: String , fontSize : Int): Boolean {

        return try {
            val noteE = NoteEntity(id = id, title = title, body = body, date = date , fontSize = fontSize)
            noteDAO.updateNote(noteE)
            true
        } catch (e: Exception) {
            Log.e("TAG_Error Update", e.message.toString())
            false
        }
    }


}