package com.ahj.noteapplication.feature.authentication.domain.model

data class VerifyOtpModel(
    val email: String,
    val otp: String
)