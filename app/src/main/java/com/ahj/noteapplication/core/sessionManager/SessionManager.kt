package com.ahj.noteapplication.core.sessionManager

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val auth: Auth
) {


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

    suspend fun userEmail(): String {

        auth.awaitInitialization()
        return requireNotNull(auth.currentUserOrNull()?.email)
    }


    suspend fun getUserId(): String {
        auth.awaitInitialization()

        return requireNotNull(
            auth.currentUserOrNull()?.id
        ) {
            "user authentication failed"
        }
    }

    fun validateToken(): SharedFlow<SessionStatus> = auth.sessionStatus

    suspend fun deleteSession() = auth.sessionManager.deleteSession()




}