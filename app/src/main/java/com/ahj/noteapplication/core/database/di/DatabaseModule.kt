package com.ahj.noteapplication.core.database.di

import android.content.Context
import androidx.room.Room
import com.ahj.noteapplication.core.database.MyDatabase
import com.ahj.noteapplication.core.database.migration.MIGRATION_1_2
import com.ahj.noteapplication.feature.note.data.local.DAO.NoteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase =
        Room.databaseBuilder(
            context = context,
            name = MyDatabase.NOTE_DB,
            klass = MyDatabase::class.java
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration(false)
            .build()


    @Provides
    @Singleton
    fun provideNoteDao(myDatabase: MyDatabase): NoteDAO =
        myDatabase.noteDao()
}