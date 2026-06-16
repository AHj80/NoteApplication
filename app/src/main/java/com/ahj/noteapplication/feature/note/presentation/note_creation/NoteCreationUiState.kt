package com.ahj.noteapplication.feature.note.presentation.note_creation

import androidx.compose.material3.SnackbarHostState


data class NoteCreationUiState(
    val stateTitle : String = "",
    val stateBody : String = "",
    val message : String? = null,
    val fontList : List<Int> = listOf(16, 20, 26, 32),
    val stateFont : Int = 16,
    val stateDropDown : Boolean = false,
    val currentId : Int = 0,
    val isChanged : Boolean = false,
    val snackBar : SnackbarHostState = SnackbarHostState()
)
