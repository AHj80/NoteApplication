package com.ahj.noteapplication.feature.authentication.domain.useCase

import com.ahj.noteapplication.feature.authentication.domain.model.RegisterModel
import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject


class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(request: RegisterModel) = repository.register(request)
}