package com.ahj.noteapplication.feature.note.presentation.createNote

import com.mohamedrejeb.richeditor.model.RichTextState

data class CreateNoteUiState(
    val message: String? = null,
    val currentId: String? = null,
    val fontList: List<Int> = listOf(16, 20, 26, 32),
    val stateFont: Int = 16,
    val stateDropDown: Boolean = false,
    val isChanged: Boolean = false,
    val success: Boolean = false,
    val titleState: RichTextState = RichTextState(),
    val bodyState: RichTextState = RichTextState()
)
