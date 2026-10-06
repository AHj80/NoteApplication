package com.ahj.noteapplication.feature.note.presentation.noteList

import com.ahj.noteapplication.feature.note.domain.model.Note


sealed interface ListNoteUiEvent {

    data class IndexCurrent(val state: String): ListNoteUiEvent
    data class Navigating(val state: String? = null) : ListNoteUiEvent

    data object OnDismissHoldingClick: ListNoteUiEvent

    data class DeleteNote(val note: Note): ListNoteUiEvent

}
