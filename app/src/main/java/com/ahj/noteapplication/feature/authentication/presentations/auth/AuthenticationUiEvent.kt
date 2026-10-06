package com.ahj.noteapplication.feature.authentication.presentations.auth

import androidx.navigation.NavController


sealed interface AuthenticationUiEvent {

    data class ChangeEmail(val email: String) : AuthenticationUiEvent
    data class ChangePass(val pass: String) : AuthenticationUiEvent
    data class ChangePassConfirm(val passConfirm: String) : AuthenticationUiEvent
    data class ChangeAuthentication(val auth: Boolean) : AuthenticationUiEvent

    object OnDismiss : AuthenticationUiEvent
    object Login : AuthenticationUiEvent
    object Register : AuthenticationUiEvent
    object Transform : AuthenticationUiEvent
    object ForgetPass : AuthenticationUiEvent



}