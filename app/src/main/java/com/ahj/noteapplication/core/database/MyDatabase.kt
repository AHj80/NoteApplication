package com.ahj.noteapplication.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahj.noteapplication.feature.note.data.local.DAO.NoteDAO
import com.ahj.noteapplication.feature.note.data.local.entities.NoteEntity


@Database(
    entities = [NoteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        const val NOTE_DB = "note_db"
    }

    abstract fun noteDao(): NoteDAO
}