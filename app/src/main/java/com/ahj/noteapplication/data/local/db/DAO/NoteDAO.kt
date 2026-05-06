package com.ahj.noteapplication.data.local.db.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ahj.noteapplication.data.local.db.entitys.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Insert
    suspend fun addNote(noteEntity: NoteEntity): Long

    @Query("SELECT * FROM NoteEntity")
    fun getNoteData(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id LIMIT 1")
    suspend fun getNoteDataById(id: Int): NoteEntity?


    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)
}