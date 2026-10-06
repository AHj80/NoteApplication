package com.ahj.noteapplication.core.network

import com.ahj.noteapplication.BuildConfig
import com.ahj.noteapplication.feature.note.data.local.remote.NoteApiService
import com.ahj.noteapplication.feature.note.data.local.remote.NoteInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteApiModule {

    private val _baseUrl = BuildConfig.SUPABASE_URL

    @Provides
    @Singleton
    fun client(
        noteInterceptor: NoteInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(noteInterceptor)
            .readTimeout(35, TimeUnit.SECONDS)
            .writeTimeout(35, TimeUnit.SECONDS)
            .connectTimeout(35, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(_baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NoteApiService =
        retrofit.create(NoteApiService::class.java)

}