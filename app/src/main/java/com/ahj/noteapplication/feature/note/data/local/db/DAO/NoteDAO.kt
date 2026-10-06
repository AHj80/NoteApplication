package com.ahj.noteapplication.feature.note.data.local.db.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ahj.noteapplication.feature.note.data.local.db.entities.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NoteEntity")
    fun getNoteData(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id LIMIT 1")
    suspend fun getNoteDataById(id: String): NoteEntity?


    @Delete
    suspend fun deleteNote(note: NoteEntity)


    @Query("UPDATE NoteEntity SET synced = :sync WHERE id = :id")
    suspend fun updateSyncData(sync: Boolean, id: String)

    @Upsert
    suspend fun syncNote(noteEntity: List<NoteEntity>)
}