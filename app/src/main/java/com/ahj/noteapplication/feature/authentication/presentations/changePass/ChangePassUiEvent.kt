package com.ahj.noteapplication.feature.authentication.presentations.changePass



interface ChangePassUiEvent {

    data class ChangeCurrentPass(val state: String): ChangePassUiEvent
    data class ChangeNewPass(val state: String): ChangePassUiEvent
    data class ChangeConfirmPass(val state: String): ChangePassUiEvent

    data object ConfirmChangePass: ChangePassUiEvent
    data object CloseAlertDialog: ChangePassUiEvent
}