package com.ahj.noteapplication.feature.authentication.domain.useCase

import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject


class ResendOtpUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(email: String) = repository.resendOtp(email)

}