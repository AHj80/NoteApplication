package com.ahj.noteapplication.feature.note.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NoteEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val body: String,
    val date: String,
    val fontSize: Int = 16,
    val synced: Boolean = false,
    val isDelete: Boolean = false
)
