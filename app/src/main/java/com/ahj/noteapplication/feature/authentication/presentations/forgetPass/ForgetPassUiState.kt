package com.ahj.noteapplication.feature.authentication.presentations.forgetPass

import android.util.Patterns


data class ForgetPassUiState(
    val status: ForgetPassStatus = ForgetPassStatus.IDELE,
    val dialog: Boolean = false,
    val message: String? = null,
    val stateEmail: String = "",
    val stateOtp: String = "",
    val timer: Int = 0,
    val enableOtp: Boolean = false
) {
    val emailValid: Boolean
        get() = stateEmail.length > 9 && stateEmail.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(
            stateEmail
        ).matches()

    val otpValid: Boolean
        get() = stateOtp.isNotBlank() && stateOtp.length == 8
}