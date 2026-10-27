package com.ahj.noteapplication.feature.authentication.domain.model



data class ChangePassword(
    val newPassword: String,
    val currentPass: String
)
