package com.ahj.noteapplication.feature.note.presentation.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.usecase.DeleteNoteUseCase
import com.ahj.noteapplication.feature.note.domain.usecase.GetNoteDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNoteViewModel @Inject constructor(
    private val getNoteDataUseCase: GetNoteDataUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListNoteUiState())
    val uiState: StateFlow<ListNoteUiState> = _uiState.asStateFlow()

    init {
        getNoteData()
    }

    fun onEvent(event: ListNoteUiEvent) {

        when (event) {
            is ListNoteUiEvent.IndexCurrent -> {
                _uiState.update {
                    it.copy(
                        selectedNote = event.state
                    )
                }
            }

            is ListNoteUiEvent.OnDismissHoldingClick -> {
                _uiState.update {
                    it.copy(
                        selectedNote = ""
                    )
                }
            }


            is ListNoteUiEvent.DeleteNote -> deleteNote(event.note)

            else -> {}
        }

    }


    private fun getNoteData() {

        viewModelScope.launch {
            getNoteDataUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            message = e.message
                        )
                    }
                }
                .onEach { list ->

                    if (list.isEmpty()) {
                        _uiState.update {
                            it.copy(note = list)
                        }
                    } else {
                        _uiState.update {
                            it.copy(note = list)
                        }
                    }

                }.launchIn(viewModelScope)

        }
    }


    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)

            _uiState.update { it.copy(message = "با موفقیت حذف گردید") }
        }
    }


}