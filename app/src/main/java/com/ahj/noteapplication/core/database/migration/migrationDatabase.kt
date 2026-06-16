package com.ahj.noteapplication.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase){
        db.execSQL("ALTER TABLE NoteEntity ADD COLUMN fontSize INTEGER NOT NULL DEFAULT 16")
    }
}
