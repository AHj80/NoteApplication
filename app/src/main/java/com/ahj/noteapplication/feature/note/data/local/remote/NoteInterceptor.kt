package com.ahj.noteapplication.feature.note.data.local.remote

import com.ahj.noteapplication.BuildConfig
import com.ahj.noteapplication.core.common.utils.Upsert
import com.ahj.noteapplication.core.sessionManager.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject


class NoteInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = sessionManager.accessToken()
        val invocation = request.tag(Invocation::class.java)


        val newRequest = request.newBuilder()
            .addHeader("apikey", BuildConfig.SUPABASE_APIKEY)
            .addHeader("Prefer", "return=representation")
            .apply {
                if (!token.isNullOrBlank()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .apply {
                if (invocation?.method()?.isAnnotationPresent(Upsert::class.java) == true)
                    addHeader("Prefer", "resolution=merge-duplicates")
            }
            .build()

        return chain.proceed(newRequest)

    }
}