package com.ahj.noteapplication.feature.note.data.local.remote


import com.ahj.noteapplication.core.common.utils.Upsert
import com.ahj.noteapplication.feature.note.data.local.remote.dto.AIRequestDto
import com.ahj.noteapplication.feature.note.data.local.remote.dto.AIResponseDto
import com.ahj.noteapplication.feature.note.data.local.remote.dto.NoteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteApiService {

    @Upsert
    @POST("rest/v1/Notes")
    suspend fun insertNote(
        @Body note: NoteDto
    ): Response<List<NoteDto>>

    @GET("rest/v1/Notes")
    suspend fun getAllNote(): Response<List<NoteDto>>


    @DELETE("rest/v1/Notes")
    suspend fun deleteNote(
        @Query("id") id: String
    ): Response<List<NoteDto>>

    @Upsert
    @POST("rest/v1/Notes")
    suspend fun unsyncedData(
        @Body unSynced: List<NoteDto>
    ): Response<List<NoteDto>>

    @POST("functions/v1/gemini")
    suspend fun aiRequest(
        @Body prompt: AIRequestDto
    ): Response<AIResponseDto>
}

