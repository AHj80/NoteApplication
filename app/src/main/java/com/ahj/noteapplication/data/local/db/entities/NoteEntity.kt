package com.ahj.noteapplication.data.local.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val body: String,
    val date: String,
    val fontSize: Int = 16
)
