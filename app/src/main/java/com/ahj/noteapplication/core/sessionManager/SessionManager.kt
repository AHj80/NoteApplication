package com.ahj.noteapplication.core.sessionManager

import io.github.jan.supabase.auth.Auth
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val auth: Auth
) {

    val getUserId = auth.currentUserOrNull()?.id?:""

    suspend fun getSessionResult(): Boolean {
        return try {
            auth.awaitInitialization()
            val session = auth.currentSessionOrNull()
            session != null
        } catch (e: Exception) {
            false
        }
    }

    fun accessToken(): String? = auth.currentAccessTokenOrNull()

}