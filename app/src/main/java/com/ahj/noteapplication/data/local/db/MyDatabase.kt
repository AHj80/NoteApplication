package com.ahj.noteapplication.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahj.noteapplication.data.local.db.DAO.NoteDAO
import com.ahj.noteapplication.data.local.db.entitys.NoteEntity


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