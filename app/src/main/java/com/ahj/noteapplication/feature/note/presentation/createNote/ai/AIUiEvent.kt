package com.ahj.noteapplication.feature.note.presentation.createNote.ai

import com.ahj.noteapplication.feature.note.domain.model.TextActions

sealed interface AIUiEvent {

    data class Action(val body: String, val actions: TextActions) : AIUiEvent

}