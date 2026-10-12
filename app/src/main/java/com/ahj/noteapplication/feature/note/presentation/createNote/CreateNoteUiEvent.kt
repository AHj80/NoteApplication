package com.ahj.noteapplication.feature.note.presentation.createNote

import com.mohamedrejeb.richeditor.model.RichTextState

sealed interface CreateNoteUiEvent {

    data class BoldText(val state: RichTextState): CreateNoteUiEvent
    data class ItalicText(val state: RichTextState): CreateNoteUiEvent
    data class UnderlineText(val state: RichTextState): CreateNoteUiEvent
    data class EditFontSize(val state: Int): CreateNoteUiEvent
    data class SelectingFontSize(val state: Boolean): CreateNoteUiEvent


    data object SaveNote: CreateNoteUiEvent
    data object CloseAlertDialog: CreateNoteUiEvent


}