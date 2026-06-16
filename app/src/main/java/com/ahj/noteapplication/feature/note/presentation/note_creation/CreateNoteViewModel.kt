package com.ahj.noteapplication.feature.note.presentation.note_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.usecase.AddNoteUseCase
import com.ahj.noteapplication.feature.note.domain.usecase.GetNoteByIdUseCase
import com.ahj.noteapplication.feature.note.domain.usecase.PersianDateUseCase
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
    private val persianDateUseCase: PersianDateUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteCreationUiState())
    val uiState: StateFlow<NoteCreationUiState> = _uiState.asStateFlow()

    fun saveOrEditNote() {
        val state = _uiState.value

        viewModelScope.launch {
            if (_uiState.value.currentId == 0) {
                addNoteUseCase(
                    Note(
                        title = state.stateTitle,
                        body = state.stateBody,
                        date = persianDate(),
                    )
                )
                    .onSuccess { longId ->
                        _uiState.update {
                            it.copy(
                                currentId = longId.toInt(),
                                message = "ذخیره گردید",
                                isChanged = false
                            )
                        }
                    }
                    .onFailure {
                        _uiState.update {
                            it.copy(
                                message = "عملیات ناموفق بود"
                            )
                        }
                    }
            } else {
                val result = updateNoteUseCase(
                    state.currentId,
                    state.stateTitle,
                    state.stateBody,
                    state.stateFont,
                    persianDate()
                )
                _uiState.update {
                    it.copy(
                        message = if (result) "تغییرات ذخیره گردید" else "ذخیره سازی تغییرات همراه با خطا بود",
                        isChanged = false
                    )

                }
            }
        }

    }

    fun getNoteById(id: Int) {

        if (id > 0) {

            viewModelScope.launch {
                getNoteByIdUseCase(id)?.let { data->
                    _uiState.update {
                        it.copy(
                            currentId = data.id,
                            stateTitle = data.title,
                            stateBody = data.body,
                            stateFont = data.fontSize,
                            isChanged = false
                        )
                    }
                }
            }
        }
    }

    fun persianDate() = persianDateUseCase()

    fun updateTextTitle(text: String) {
        _uiState.update {
            it.copy(stateTitle = text, isChanged = true)
        }
    }

    fun updateTextBody(text: String) {
        _uiState.update {
            it.copy(stateBody = text, isChanged = true)
        }
    }

    fun selectFontSize(size: Int) {

        _uiState.update {
            it.copy(
                stateFont = size,
                stateDropDown = false,
                isChanged = true
            )
        }
    }

    fun setDropDownMenu(status : Boolean){

        _uiState.update {
            it.copy(
                stateDropDown = status
            )
        }
    }

    fun cleanMessage() = _uiState.update { it.copy(message = null) }


}