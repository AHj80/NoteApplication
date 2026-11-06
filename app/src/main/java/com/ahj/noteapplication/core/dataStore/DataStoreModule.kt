package com.ahj.noteapplication.core.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("note_application")

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStorePref(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        context.dataStore


}