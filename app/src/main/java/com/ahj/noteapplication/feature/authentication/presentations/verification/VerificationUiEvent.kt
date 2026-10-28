package com.ahj.noteapplication.feature.authentication.presentations.verification

interface VerificationUiEvent {

    data class ChangeOTPState(val state: String): VerificationUiEvent

    data object OnDismiss: VerificationUiEvent
    data object ConfirmClick: VerificationUiEvent
    data object ResendOtp: VerificationUiEvent

}