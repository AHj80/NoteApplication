package com.ahj.noteapplication.feature.authentication.presentations.verification


data class VerificationUiState(
    val status: VerificationStatus = VerificationStatus.IDELE,
    val message: String? = null,
    val alert: Boolean = false,
    val timer: Int = 60,
    val stateOtp: String = "",
    val enabled: Boolean = false
)
