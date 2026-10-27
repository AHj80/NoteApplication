package com.ahj.noteapplication.feature.authentication.presentations.changePass

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.feature.authentication.domain.model.ChangePassword
import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangePassViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePassUiState())
    val uiState: StateFlow<ChangePassUiState> = _uiState.asStateFlow()


    fun onEvent(event: ChangePassUiEvent) {

        when (event) {
            is ChangePassUiEvent.ConfirmChangePass -> changePass()

            is ChangePassUiEvent.ChangeConfirmPass -> {
                _uiState.update { it.copy(stateConfirmPass = event.state) }
            }

            is ChangePassUiEvent.ChangeCurrentPass -> {
                _uiState.update { it.copy(stateCurrentPass = event.state) }
            }

            is ChangePassUiEvent.ChangeNewPass -> {
                _uiState.update { it.copy(stateNewPass = event.state) }
            }

            is ChangePassUiEvent.CloseAlertDialog -> {
                _uiState.update { it.copy(stateDialog = false) }
            }

        }

    }


    private fun changePass() {
        val state = _uiState.value
        viewModelScope.launch {
            val passData = ChangePassword(state.stateNewPass, state.stateCurrentPass)

            _uiState.update { it.copy(status = ChangePassStatus.LOADING, message = null) }
            repository.changePass(passData)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            status = ChangePassStatus.SUCCESS,
                            message = "رمز عبور ب موفقیت تغییر یافت",
                            stateDialog = true
                        )
                    }
                }
                .onFailure { error ->
                    Log.e("ahj_Error", error.message.toString())
                    _uiState.update {
                        it.copy(
                            status = ChangePassStatus.ERROR,
                            message = error.message,
                            stateDialog = true
                        )
                    }
                }
        }
    }
}