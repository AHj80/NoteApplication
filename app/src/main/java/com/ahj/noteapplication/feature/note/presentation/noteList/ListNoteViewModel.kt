package com.ahj.noteapplication.feature.note.presentation.noteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.core.common.utils.DispatcherIO
import com.ahj.noteapplication.core.sessionManager.SessionManager
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import com.ahj.noteapplication.feature.note.domain.usecase.DeleteNoteUseCase
import com.ahj.noteapplication.feature.note.domain.usecase.GetNoteDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val sessionManager: SessionManager,
    private val repositoryNote: RepositoryNote,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
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
                        selectedNote = null
                    )
                }
            }


            is ListNoteUiEvent.DeleteNote -> {
                deleteNote(event.note)
            }

            is ListNoteUiEvent.CloseDialog -> {
                _uiState.update { it.copy(stateDialog = false) }
            }

            is ListNoteUiEvent.Logout -> logout()

            else -> {}
        }

    }


    private fun getNoteData() {
        viewModelScope.launch {
            _uiState.update { it.copy(status = ListScreenStatus.LOADING, statusMessage = null) }
            getNoteDataUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            statusMessage = e.message,
                            status = ListScreenStatus.ERROR,
                            isUnsynced = false
                        )
                    }
                }
                .collect { data ->

                    if (data.notes.isEmpty()) {
                        _uiState.update {
                            it.copy(
                                note = data.notes,
                                status = ListScreenStatus.EMPTY,
                                statusMessage = "یادداشتی وجود ندارد.",
                                isUnsynced = data.exists,
                                userEmail = sessionManager.userEmail()
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                note = data.notes,
                                statusMessage = null,
                                status = ListScreenStatus.SUCCESS,
                                isUnsynced = data.exists,
                                userEmail = sessionManager.userEmail()
                            )
                        }
                    }

                }


        }
    }


    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            val delete = deleteNoteUseCase(note)
            if (delete)
                _uiState.update { it.copy(stateDialog = true, message = "با موفقیت حذف گردید") }
        }
    }

    private fun logout() {
        viewModelScope.launch(dispatcherIO) {
            repositoryNote.logout()
        }
    }


}