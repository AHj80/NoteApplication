package com.ahj.noteapplication.feature.note.presentation.noteList

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.mohamedrejeb.richeditor.model.RichTextState

data class ListNoteUiState(
    val note: List<Note> = emptyList(),
    val statusMessage: String? = null,
    val message: String? = null,
    val stateSearching: RichTextState = RichTextState(),
    val status: ListScreenStatus = ListScreenStatus.EMPTY,
    val windowHolding: Boolean = false,
    val selectedNote: String? = null,
    val stateDialog: Boolean = false
)

