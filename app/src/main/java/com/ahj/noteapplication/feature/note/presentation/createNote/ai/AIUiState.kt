package com.ahj.noteapplication.feature.note.presentation.createNote.ai

data class AIUiState(
    val status: AIStatus = AIStatus.IDLE,
    val aiResponse: String? = null
)
