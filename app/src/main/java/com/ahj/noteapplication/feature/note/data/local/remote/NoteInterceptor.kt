package com.ahj.noteapplication.feature.note.data.local.remote

import com.ahj.noteapplication.BuildConfig
import com.ahj.noteapplication.core.sessionManager.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class NoteInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val token = sessionManager.accessToken()

        val newRequest = request.newBuilder()
            .addHeader("apikey", BuildConfig.SUPABASE_APIKEY)
            .addHeader("Prefer", "return=representation")
            .apply {
                if (!token.isNullOrBlank()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(newRequest)

    }
}