package com.ahj.noteapplication.feature.note.presentation.createNote

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.utils.PersianDate
import com.ahj.noteapplication.core.common.utils.stripMarkdown
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


    init {
        getNoteById()
    }


    fun onEvent(onEvent: CreateNoteUiEvent) {

        when (onEvent) {
            is CreateNoteUiEvent.SaveNote ->{
                val title = _uiState.value.titleState.annotatedString.text.isBlank()
                val body = _uiState.value.bodyState.annotatedString.text.isBlank()
                if (title || body)
                    _uiState.update { it.copy(message = "عنوان و متن بدنه ی یادداشت نمیتواند خالی باشد" , status = CreateNoteStatus.ERROR , dialog = true) }
                else
                    upsertNote()
            }
            is CreateNoteUiEvent.BoldText -> {
                onEvent.state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.ExtraBold))
            }

            is CreateNoteUiEvent.ItalicText -> {
                onEvent.state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
            }

            is CreateNoteUiEvent.UnderlineText -> {
                onEvent.state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
            }

            is CreateNoteUiEvent.SelectingFontSize -> {
                _uiState.update { it.copy(selectingFontSize = !it.selectingFontSize) }
            }

            is CreateNoteUiEvent.EditFontSize -> {
                _uiState.update { it.copy(stateFont = onEvent.state) }
            }

            is CreateNoteUiEvent.CloseAlertDialog -> {
                _uiState.update { it.copy(status = CreateNoteStatus.IDLE , dialog = false) }
            }

        }

    }




    private fun upsertNote() {

        val state = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(status = CreateNoteStatus.LOADING , message = null) }
            if (_args.id.isNullOrBlank()) {
                addNoteUseCase(
                    state.titleState.annotatedString.text,
                    state.bodyState.toMarkdown(),
                    persianDate.getShortDate(),
                    state.stateFont
                )
                    .onSuccess {
                        _uiState.update {
                            it.copy(message = "ذخیره گردید" , dialog = true , status = CreateNoteStatus.SUCCESS)
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
                    body = state.bodyState.toMarkdown(),
                    date = persianDate.getCustomDate(),
                    fontSize = state.stateFont
                )
                updateNoteUseCase(note)
                    .onSuccess {
                        _uiState.update {
                            it.copy(message = "ذخیره گردید" , dialog = true , status = CreateNoteStatus.SUCCESS)
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

                    _uiState.update {
                        it.copy(
                            stateFont = note.fontSize,
                            titleState = it.titleState.setText(note.title),
                            bodyState = it.bodyState.setMarkdown(note.body.stripMarkdown())
                        )
                    }
                }
        }
    }

}