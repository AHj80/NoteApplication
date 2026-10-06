package com.ahj.noteapplication.feature.note.di

import com.ahj.noteapplication.feature.note.data.repository.NoteRepositoryImpl
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {



    @Binds
    abstract fun provideRepositoryImpl(noteRepositoryImpl: NoteRepositoryImpl): RepositoryNote
}