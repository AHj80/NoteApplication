package com.ahj.noteapplication.feature.authentication.presentations.forgetPass


data class ForgetPassUiState(
    val status: ForgetPassStatus = ForgetPassStatus.IDELE,
    val dialog: Boolean = false,
    val message: String? = null,
    val stateEmail: String = "",
    val stateOtp: String = "",
    val timer: Int = 0,
    val enableOtp: Boolean = false
) {

    val otpValid: Boolean
        get() = stateOtp.isNotBlank() && stateOtp.length == 8
}