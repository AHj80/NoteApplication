package com.ahj.noteapplication.viewModel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val repositoryNote: RepositoryNote
) : ViewModel() {

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message

    var stateTitle by mutableStateOf(String())

    var stateBody by mutableStateOf(String())

    var listSize = mutableListOf(16, 20, 26, 32)

    private var _stateFontSize = MutableStateFlow(16)
    val stateFontSize : StateFlow<Int> = _stateFontSize

    var stateDropDown by mutableStateOf(false)

    var currentId by mutableIntStateOf(0)

    var isChanged by mutableStateOf(false)

    private var _stateSnackBar = MutableStateFlow(SnackbarHostState())
    val stateSnackBar : StateFlow<SnackbarHostState> = _stateSnackBar


    fun saveNote() {
        val noteE = NoteEntity(title = stateTitle, body = stateBody, date = repositoryNote.date , fontSize = _stateFontSize.value)

        viewModelScope.launch {
            if (currentId == 0) {
                repositoryNote.addNote(noteE)
                    .onSuccess {
                        currentId = it.toInt()
                        _message.emit("ذخیره گردید")
                        isChanged = false
                    }
                    .onFailure {
                        _message.emit("عملیات ناموفق بود")
                    }
            } else {
                val result = repositoryNote.updateNoteData(currentId, stateTitle, stateBody , _stateFontSize.value)
                _message.emit(
                    if (result) "ذخیره شد" else "عملیات همراه با خطا بود"
                )
                isChanged = false

            }

        }
    }


    fun getNoteById(id: Int) {
        if (id > 0) {
            viewModelScope.launch {
                repositoryNote.getNoteDataById(id)?.let {
                    currentId = id
                    stateTitle = it.title
                    stateBody = it.body
                    _stateFontSize.value = it.fontSize
                    isChanged = false
                }
            }
        }

    }



    fun onChangedTitle(value: String) {
        stateTitle = value
        isChanged = true
    }

    fun onChangedBody(value: String) {
        stateBody = value
        isChanged = true
    }

    fun selectFontSize(size: Int){

        _stateFontSize.value = size
        stateDropDown = false
        isChanged = true
    }



}
