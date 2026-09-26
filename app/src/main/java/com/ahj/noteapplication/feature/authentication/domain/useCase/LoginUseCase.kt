package com.ahj.noteapplication.feature.authentication.domain.useCase

import com.ahj.noteapplication.feature.authentication.domain.model.LoginModel
import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke(requestModel: LoginModel) = repository.login(requestModel)
}