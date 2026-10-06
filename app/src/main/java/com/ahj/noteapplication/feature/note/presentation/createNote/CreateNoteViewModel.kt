package com.ahj.noteapplication.feature.note.presentation.createNote

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.utils.PersianDate
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.usecase.AddNoteUseCase
import com.ahj.noteapplication.feature.note.domain.usecase.GetNoteByIdUseCase
import com.ahj.noteapplication.feature.note.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val persianDate: PersianDate,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateNoteUiState())
    val uiState: StateFlow<CreateNoteUiState> = _uiState.asStateFlow()
    private val _args = savedStateHandle.toRoute<Screens.CreateScreen>()
    var stateTitle = TextFieldState("یادداشت جدید")
    var stateBody = TextFieldState()


    init {
        getNoteById()
    }


    fun onEvent(onEvent: CreateNoteUiEvent) {

        when (onEvent) {
            is CreateNoteUiEvent.SaveNote -> upsertNote()
        }

    }


    private fun upsertNote() {
        val state = _uiState.value
        viewModelScope.launch {
            if (_args.id.isNullOrBlank()) {
                addNoteUseCase(
                    stateTitle.text.toString(),
                    stateBody.text.toString(),
                    persianDate.getShortDate(),
                    state.stateFont
                )
                    .onSuccess {
                        _uiState.update {
                            it.copy(message = "ذخیره گردید")
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(message = error.message)
                        }
                    }
            } else {
                val note = Note(
                    _args.id,
                    title = state.titleState.annotatedString.text,
                    body = state.bodyState.annotatedString.text,
                    date = persianDate.getCustomDate(),
                    fontSize = state.stateFont
                )
                updateNoteUseCase(note)
                    .onSuccess {
                        _uiState.update {
                            it.copy(message = "ذخیره گردید")
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(message = error.message)
                        }
                    }
            }
        }

    }

    private fun getNoteById() {
        viewModelScope.launch {
            if (!_args.id.isNullOrBlank())
                getNoteByIdUseCase(_args.id)?.let { note ->
                    stateTitle.setTextAndPlaceCursorAtEnd(note.title)
                    stateBody.setTextAndPlaceCursorAtEnd(note.body)
                    _uiState.update {
                        it.copy(
                            stateFont = note.fontSize
                        )
                    }
                }
        }
    }

}