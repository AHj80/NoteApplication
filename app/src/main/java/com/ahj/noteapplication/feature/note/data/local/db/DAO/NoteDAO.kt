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

    @Query("SELECT * FROM NoteEntity WHERE isDelete = 0")
    fun getNoteData(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id LIMIT 1")
    suspend fun getNoteDataById(id: String): NoteEntity?


    @Query("UPDATE NoteEntity SET synced = 0 , isDelete = 1 WHERE id = :id")
    suspend fun softDeleteNote(id: String)

    @Query("DELETE FROM NoteEntity WHERE id = :id ")
    suspend fun hardDeleteNote(id: String)


    @Query("UPDATE NoteEntity SET synced = :sync WHERE id = :id")
    suspend fun updateSyncData(sync: Boolean, id: String)


    @Query("SELECT COUNT(*) FROM NoteEntity")
    suspend fun getCountNote(): Int

    @Upsert
    suspend fun syncNote(noteEntity: List<NoteEntity>)

    @Query("SELECT * FROM NoteEntity WHERE synced = 0")
    suspend fun getNoteUnsynced(): List<NoteEntity>
}