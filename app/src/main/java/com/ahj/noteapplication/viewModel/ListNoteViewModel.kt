package com.ahj.noteapplication.viewModel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.data.local.db.entitys.NoteEntity
import com.ahj.noteapplication.repository.RepositoryNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListNoteViewModel @Inject constructor(
    private val repositoryNote: RepositoryNote
) : ViewModel() {

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message

    private val _note = MutableStateFlow<List<NoteEntity>>(emptyList())
    val note: StateFlow<List<NoteEntity>> = _note

    var stateSearching by mutableStateOf(String())

    private var _stateSnackBar = MutableStateFlow(SnackbarHostState())
    val stateSnackBar: StateFlow<SnackbarHostState> = _stateSnackBar


    init {
        getNoteData()

    }


    fun getNoteData() {
        viewModelScope.launch {
            repositoryNote.getNoteData().collectLatest {
                _note.value = it
            }


        }
    }


    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            val result = repositoryNote.deleteNote(note)

            _message.emit(
                if (result) "یادداشت حذف شد" else "عملیات همراه با خطا بود"
            )
        }
    }




}