package com.ahj.noteapplication.feature.note.di

import com.ahj.noteapplication.core.common.utils.persiandate.PersianDate
import com.ahj.noteapplication.feature.note.data.local.DAO.NoteDAO
import com.ahj.noteapplication.feature.note.data.repository.RepositoryImpl
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {



    @Binds
    abstract fun provideRepositoryImpl(repositoryImpl: RepositoryImpl): RepositoryNote
}