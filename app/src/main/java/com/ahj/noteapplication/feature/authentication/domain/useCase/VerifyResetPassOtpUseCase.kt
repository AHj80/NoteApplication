package com.ahj.noteapplication.feature.authentication.domain.useCase

import com.ahj.noteapplication.feature.authentication.domain.model.VerifyOtpModel
import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject


class VerifyResetPassOtpUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(request: VerifyOtpModel) = repository.verifyResetPassOtp(request)
}