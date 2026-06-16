package com.ahj.noteapplication.feature.note.presentation.note_list

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNoteViewModel @Inject constructor(
    private val getNoteDataUseCase: GetNoteDataUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()

    init {
        getNoteData()
    }

    fun getNoteData() {

        viewModelScope.launch {
            getNoteDataUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            message = e.message
                        )
                    }
                }
                .collect { data ->

                    _uiState.update {
                        it.copy(
                            note = data
                        )
                    }


                }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)

            _uiState.update { it.copy(message = "با موفقیت حذف گردید") }
        }
    }


    fun updateStateSearch(text: String) {
        _uiState.update {
            it.copy(
                stateSearching = text
            )
        }
    }

    fun cleanMessage() = _uiState.update { it.copy(message = null) }


}