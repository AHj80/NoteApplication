package com.ahj.noteapplication.feature.note.data.local.remote


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NoteApiService {

    @Headers("Prefer: resolution=merge-duplicates")
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
}