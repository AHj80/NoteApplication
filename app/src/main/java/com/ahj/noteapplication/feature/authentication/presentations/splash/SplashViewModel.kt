package com.ahj.noteapplication.feature.authentication.presentations.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.core.sessionManager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()


    init {
        getSession()
    }

    fun getSession() {

        viewModelScope.launch {
            delay(2000.milliseconds)
            Log.i("ahj_splash" , "delay")
            val session = sessionManager.getSessionResult()
            _uiState.update {
                it.copy(
                    navigation = session
                )
            }


        }

    }
}