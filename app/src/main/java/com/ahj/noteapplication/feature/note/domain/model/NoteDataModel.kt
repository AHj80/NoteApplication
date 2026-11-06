package com.ahj.noteapplication.feature.note.domain.model

data class NoteDataModel(
    val notes: List<Note>,
    val exists: Boolean,
    val biometric: Boolean,
    val email: String
)
