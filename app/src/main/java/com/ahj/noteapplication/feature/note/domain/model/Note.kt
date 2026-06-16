package com.ahj.noteapplication.feature.note.domain.model

data class Note(
    val id: Int = 0,
    val title: String,
    val body: String,
    val date: String,
    val fontSize: Int = 16
)