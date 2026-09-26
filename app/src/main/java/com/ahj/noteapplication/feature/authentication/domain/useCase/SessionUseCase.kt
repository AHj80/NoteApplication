package com.ahj.noteapplication.feature.authentication.domain.useCase

import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject


class SessionUseCase @Inject constructor(
    private val repository: AuthenticationRepository
){

    suspend operator fun invoke() = repository.getSession()
}