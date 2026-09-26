package com.ahj.noteapplication.feature.authentication.presentations.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.feature.authentication.domain.model.VerifyOtpModel
import com.ahj.noteapplication.feature.authentication.domain.useCase.ResendOtpUseCase
import com.ahj.noteapplication.feature.authentication.domain.useCase.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase,
    private val resendOtpUseCase: ResendOtpUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerificationUiState())
    val uiState: StateFlow<VerificationUiState> = _uiState.asStateFlow()

    private val _args = savedStateHandle.toRoute<Screens.VerificationScreen>()


    init {
        timer()
    }


    fun verificationOtp() {

        val verifyRequest = VerifyOtpModel(_args.email, _uiState.value.stateOtp)

        viewModelScope.launch {
            _uiState.update { it.copy(status = VerificationStatus.LOADING, message = null) }
            verifyOtpUseCase(verifyRequest)

                .onSuccess {
                    _uiState.update {
                        it.copy(
                            status = VerificationStatus.SUCCESS,
                            message = null
                        )
                    }
                }
                .onFailure { error ->

                    _uiState.update {
                        it.copy(
                            status = VerificationStatus.ERROR,
                            message = error.message,
                            alert = true
                        )
                    }
                }
        }
    }


    fun changeStateOtp(state: String) = _uiState.update { it.copy(stateOtp = state) }


    fun resendOtp() {

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    status = VerificationStatus.LOADING,
                    message = null
                )
            }
            resendOtpUseCase(_args.email)
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            message = "کد با موفقیت به آدرس :  ارسال گردید",
                            status = VerificationStatus.IDELE,
                            alert = true
                        )
                    }
                    timer()
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            message = error.message,
                            status = VerificationStatus.ERROR,
                            alert = true
                        )
                    }
                }
        }
    }

    fun onDismiss() {
        _uiState.update { it.copy(status = VerificationStatus.IDELE, alert = false) }
    }

    fun validating(): Boolean {
        val state = _uiState.value
        return state.stateOtp.isNotBlank() && state.stateOtp.length == 8
    }

    private fun timer() {

        viewModelScope.launch {
            for (i in 59 downTo 0) {
                delay(1000)
                _uiState.update { it.copy(timer = i) }

            }
        }

    }

}