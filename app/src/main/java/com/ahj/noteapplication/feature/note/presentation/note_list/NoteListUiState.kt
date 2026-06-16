package com.ahj.noteapplication.feature.note.presentation.note_list

import com.ahj.noteapplication.feature.note.domain.model.Note

data class NoteListUiState(
    val note: List<Note> = emptyList(),
    val message: String? = null,
    val stateSearching: String = "",

)
