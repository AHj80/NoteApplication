package com.ahj.noteapplication.feature.note.presentation.noteList

import androidx.compose.foundation.text.input.TextFieldState
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.mohamedrejeb.richeditor.model.RichTextState

data class ListNoteUiState(
    val note: List<Note> = emptyList(),
    val message: String? = null,
    val stateSearching: RichTextState = RichTextState(),
    val status: ListScreenStatus = ListScreenStatus.EMPTY,
    val windowHolding: Boolean = false,
    val selectedNote: String? = null
){


}

