package com.ahj.noteapplication.feature.authentication.presentations.splash


data class SplashUiState(
    val status: SplashStatus = SplashStatus.NON_BIOMETRIC,
    val navigation: Boolean? = null,
)