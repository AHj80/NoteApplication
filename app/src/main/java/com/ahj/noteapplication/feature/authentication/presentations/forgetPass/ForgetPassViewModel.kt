package com.ahj.noteapplication.feature.authentication.presentations.forgetPass

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.feature.authentication.domain.model.VerifyOtpModel
import com.ahj.noteapplication.feature.authentication.domain.useCase.SendResetPassOtpUseCase
import com.ahj.noteapplication.feature.authentication.domain.useCase.VerifyResetPassOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgetPassViewModel @Inject constructor(
    private val sendResetPassOtpUseCase: SendResetPassOtpUseCase,
    private val verifyResetPassOtpUseCase: VerifyResetPassOtpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgetPassUiState())
    val uiState: StateFlow<ForgetPassUiState> = _uiState.asStateFlow()
    private var _job: Job? = null


    fun onEvent(onEvent: ForgetPassUiEvent) {
        when (onEvent) {
            is ForgetPassUiEvent.ChangeStateEmail -> {
                _uiState.update { it.copy(stateEmail = onEvent.state) }
            }

            is ForgetPassUiEvent.ChangeStateOtp -> {
                _uiState.update { it.copy(stateOtp = onEvent.state) }
            }

            is ForgetPassUiEvent.SendOtp -> sendOtp()

            is ForgetPassUiEvent.VerifyOtp -> verifyOtp()

            is ForgetPassUiEvent.OnDismiss -> _uiState.update { it.copy(dialog = false) }
        }
    }


    private fun sendOtp() {
        viewModelScope.launch {
            _uiState.update { it.copy(status = ForgetPassStatus.LOADING, message = null) }
            sendResetPassOtpUseCase(_uiState.value.stateEmail)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            status = ForgetPassStatus.EMAIL_SUCCESS,
                            dialog = true,
                            message = "کد تایید با موفقیت ارسال گردید، در صورت نیاز به ویرایش ایمیل و دریافت مجدد کد تا منقضی شدن کد ارسال شده منتظر بمانید",
                            enableOtp = true
                        )
                    }
                    timer()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            status = ForgetPassStatus.EMAIL_ERROR,
                            dialog = true,
                            message = error.message,
                            enableOtp = false
                        )
                    }
                }
        }
    }


    private fun verifyOtp() {
        val state = _uiState.value
        val verifyOtp = VerifyOtpModel(state.stateEmail, state.stateOtp)
        viewModelScope.launch {
            _uiState.update { it.copy(status = ForgetPassStatus.LOADING, message = null) }

            verifyResetPassOtpUseCase(verifyOtp)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            status = ForgetPassStatus.VERIFY_SUCCESS,
                            message = null
                        )
                    }

                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            status = ForgetPassStatus.VERIFY_ERROR,
                            dialog = true,
                            message = error.message,
                        )
                    }
                }
        }
    }

    private fun timer() {
        _job?.cancel()
        _job = viewModelScope.launch {
            for (i in 59 downTo 0) {
                delay(1000)
                _uiState.update { it.copy(timer = i) }
            }
            _uiState.update { it.copy(enableOtp = false) }
        }

    }

}