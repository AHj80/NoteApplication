package com.ahj.noteapplication.feature.authentication.data.di

import com.ahj.noteapplication.feature.authentication.data.repository.AuthenticationRepositoryImpl
import com.ahj.noteapplication.feature.authentication.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

}