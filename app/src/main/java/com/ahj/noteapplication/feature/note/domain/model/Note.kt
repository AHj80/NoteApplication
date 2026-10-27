package com.ahj.noteapplication.feature.note.domain.model

data class Note(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
    val fontSize: Int = 16,
    val synced: Boolean = false
)