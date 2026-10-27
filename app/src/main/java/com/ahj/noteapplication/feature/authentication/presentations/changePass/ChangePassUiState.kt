package com.ahj.noteapplication.feature.authentication.presentations.changePass

data class ChangePassUiState(
    val status: ChangePassStatus = ChangePassStatus.IDLE,
    val message: String? = null,
    val stateCurrentPass: String = "",
    val stateNewPass: String = "",
    val stateConfirmPass: String = "",
    val stateDialog: Boolean = false
)
