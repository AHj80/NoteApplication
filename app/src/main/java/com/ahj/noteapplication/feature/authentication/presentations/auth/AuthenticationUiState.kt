package com.ahj.noteapplication.feature.authentication.presentations.auth



data class AuthenticationUiState(
    val status: AuthenticationStatus = AuthenticationStatus.IDELE,
    val changingAuth: Boolean = true,
    val message: String? = null,
    val error: String? = null,
    val stateEmail: String = "",
    val statePass: String = "",
    val visualTransform: Boolean = false,
    val statePassConfirm: String = ""

)
