package com.ahj.noteapplication.feature.authentication.presentations.forgetPass

sealed interface ForgetPassUiEvent {

    data class ChangeStateEmail(val state: String) : ForgetPassUiEvent
    data class ChangeStateOtp(val state: String) : ForgetPassUiEvent
    data object SendOtp : ForgetPassUiEvent
    data object VerifyOtp : ForgetPassUiEvent
    data object OnDismiss : ForgetPassUiEvent
}