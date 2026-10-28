package com.ahj.noteapplication.feature.authentication.presentations.verification

import android.util.Log
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
import kotlin.time.Duration.Companion.milliseconds


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


    fun onEvent(event: VerificationUiEvent) {

        when (event) {

            is VerificationUiEvent.ChangeOTPState -> {
                _uiState.update { it.copy(stateOtp = event.state.filter { otp-> otp.isDigit() }.take(8)) }
            }

            is VerificationUiEvent.OnDismiss -> {
                _uiState.update { it.copy(status = VerificationStatus.IDELE, alert = false) }
            }

            is VerificationUiEvent.ResendOtp -> resendOtp()


            is VerificationUiEvent.ConfirmClick -> verificationOtp()

        }

    }


   private fun verificationOtp() {

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


    private fun resendOtp() {

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    status = VerificationStatus.LOADING,
                    message = null
                )
            }
            Log.i("ahj_resend",_args.email)
            resendOtpUseCase(_args.email)
                .onSuccess {

                    _uiState.update {
                        it.copy(
                            message = "کد تایید با موفقیت به آدرس ${_args.email} ارسال گردید",
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

    private fun timer() {

        viewModelScope.launch {
            for (i in 59 downTo 0) {
                delay(1000.milliseconds)
                _uiState.update { it.copy(timer = i) }

            }
        }

    }

}