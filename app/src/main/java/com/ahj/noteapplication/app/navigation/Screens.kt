package com.ahj.noteapplication.app.navigation

import kotlinx.serialization.Serializable


sealed class Screens {
    @Serializable
    object SplashScreen: Screens()

    @Serializable
    data object Authentication: Screens()

    @Serializable
    data object ForgetPassword: Screens()

    @Serializable
    data class VerificationScreen(val email: String): Screens()

    @Serializable
    object ListNoteScreens : Screens()

    @Serializable
    data class CreateScreen(val id: String?) : Screens()

}