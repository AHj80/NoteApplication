package com.ahj.noteapplication.feature.note.presentation.createNote.create

import com.mohamedrejeb.richeditor.model.RichTextState

data class CreateNoteUiState(
    val titleState: RichTextState = RichTextState().setText("یادداشت جدید"),
    val bodyState: RichTextState = RichTextState(),
    val status: CreateNoteStatus = CreateNoteStatus.IDLE,
    val message: String? = null,
    val fontList: List<Int> = listOf(16, 20, 26, 32),
    val stateFont: Int = 16,
    val selectingFontSize: Boolean = false,
    val dialog: Boolean = false,
    val aiBottomSheet: Boolean = false,
    val currentId: String = ""
)
