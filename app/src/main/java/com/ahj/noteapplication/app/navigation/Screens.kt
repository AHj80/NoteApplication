package com.ahj.noteapplication.app.navigation

import kotlinx.serialization.Serializable


sealed class Screens {

    @Serializable
    object ListNoteScreens : Screens()
    @Serializable
    data class CreateScreen(val id: Int) : Screens()

}