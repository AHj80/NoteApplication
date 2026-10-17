package com.ahj.noteapplication.feature.note.data.di

import com.ahj.noteapplication.feature.note.data.repository.NoteRepositoryImpl
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {



    @Binds
    @Singleton
    abstract fun provideRepositoryImpl(noteRepositoryImpl: NoteRepositoryImpl): RepositoryNote
}