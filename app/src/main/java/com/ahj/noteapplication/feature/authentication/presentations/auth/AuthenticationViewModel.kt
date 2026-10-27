package com.ahj.noteapplication.feature.authentication.presentations.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.feature.authentication.domain.model.LoginModel
import com.ahj.noteapplication.feature.authentication.domain.model.RegisterModel
import com.ahj.noteapplication.feature.authentication.domain.useCase.LoginUseCase
import com.ahj.noteapplication.feature.authentication.domain.useCase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.PasswordAuthentication
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState.asStateFlow()

    fun onEvent(event: AuthenticationUiEvent) {
        when (event) {

            is AuthenticationUiEvent.ChangeAuthentication -> {
                _uiState.update { it.copy(changingAuth = event.auth) }
            }

            is AuthenticationUiEvent.ChangeEmail -> {
                _uiState.update { it.copy(stateEmail = event.email) }

            }

            is AuthenticationUiEvent.ChangePass -> {
                _uiState.update { it.copy(statePass = event.pass) }
            }

            is AuthenticationUiEvent.ChangePassConfirm -> {
                _uiState.update { it.copy(statePassConfirm = event.passConfirm) }
            }

            is AuthenticationUiEvent.Transform -> {
                _uiState.update { it.copy(visualTransform = !it.visualTransform) }
            }

            is AuthenticationUiEvent.OnDismiss -> {
                _uiState.update { it.copy(status = AuthenticationStatus.IDELE) }
            }

            is AuthenticationUiEvent.Login -> {
                login()
            }

            is AuthenticationUiEvent.Register -> {
                register()
            }

            else -> {}

        }
    }

    private fun login() {

        val loginModel =
            LoginModel(email = _uiState.value.stateEmail, password = _uiState.value.statePass)
        viewModelScope.launch {
            _uiState.update { it.copy(status = AuthenticationStatus.LOADING, error = null) }
            loginUseCase(loginModel)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            status = AuthenticationStatus.SUCCESS_LOGIN,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            status = AuthenticationStatus.ERROR,
                            error = error.message
                        )
                    }

                }
        }

    }

    private fun register() {
        val registerModel =
            RegisterModel(email = _uiState.value.stateEmail, password = _uiState.value.statePass)
        viewModelScope.launch {
            _uiState.update { it.copy(status = AuthenticationStatus.LOADING, message = null) }
            registerUseCase(registerModel)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            status = AuthenticationStatus.SUCCESS_REGISTER,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            status = AuthenticationStatus.ERROR,
                            error = error.message
                        )
                    }

                }
        }

    }


}