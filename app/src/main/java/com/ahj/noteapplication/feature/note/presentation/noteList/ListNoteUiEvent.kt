package com.ahj.noteapplication.feature.note.presentation.noteList

import com.ahj.noteapplication.feature.note.domain.model.Note


sealed interface ListNoteUiEvent {

    data class DeleteNote(val note: Note) : ListNoteUiEvent
    data class IndexCurrent(val state: String) : ListNoteUiEvent
    data class Navigating(val state: String = "") : ListNoteUiEvent


    data object CloseDialog : ListNoteUiEvent

    data object OnDismissHoldingClick : ListNoteUiEvent
}
