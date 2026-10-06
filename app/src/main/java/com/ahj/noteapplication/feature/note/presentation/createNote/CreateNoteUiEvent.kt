package com.ahj.noteapplication.feature.note.presentation.createNote

sealed interface CreateNoteUiEvent {

    data object SaveNote: CreateNoteUiEvent

}