package com.ahj.noteapplication.feature.authentication.presentations.forgetPass


data class ForgetPassUiState(
    val status: ForgetPassStatus = ForgetPassStatus.IDELE,
    val dialog: Boolean = false,
    val error: String? = null,
    val stateEmail: String = "",
    val stateOtp: String = "",
    val timer: Int = 60,
    val enableOtp: Boolean = false
)