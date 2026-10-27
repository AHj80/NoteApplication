package com.ahj.noteapplication.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.core.sessionManager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _navigating = Channel<MainUiEvent>()
    val navigating = _navigating.receiveAsFlow()

    init {
        observerSession()
    }

    fun observerSession() {
        viewModelScope.launch {

            sessionManager.validateToken().collect { sessionStatus ->

                when (sessionStatus) {
                    is SessionStatus.NotAuthenticated, is SessionStatus.RefreshFailure -> {
                        _navigating.send(MainUiEvent.NavigateToLogin)
                        sessionManager.deleteSession()
                    }

                    else -> {}
                }

            }
        }

    }

}