package com.ahj.noteapplication.core.dataStore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SessionManager @Inject constructor(
    private val auth: Auth,
    private val dataStore: DataStore<Preferences>
) {

    companion object{
        val BIOMETRIC = booleanPreferencesKey("biometric")
    }


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

    fun userEmail(): Flow<String> {

        return flow {
            val email = auth.currentUserOrNull()?.email
            emit(email?:"Guest")
        }
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

    val biometricEnable : Flow<Boolean> = dataStore.data.map {
        it[BIOMETRIC]?:false
    }

    suspend fun changeBiometricEnabled(state: Boolean){
        dataStore.edit {
            it[BIOMETRIC] = state
        }
    }


}