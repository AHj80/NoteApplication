package com.ahj.noteapplication.feature.authentication.presentations.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.core.dataStore.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    fun onEvent(event: SplashUiEvent) {
        when (event) {
            is SplashUiEvent.NavigatingHandled -> _uiState.update {
                it.copy(navigation = null)
            }
        }


    }

    private fun getSession() {

        viewModelScope.launch {
            delay(2000.milliseconds)
            val session = sessionManager.getSessionResult()
            val biometric = sessionManager.biometricEnable.first()
            if (biometric) {
                _uiState.update {
                    it.copy(
                        navigation = session,
                        status = SplashStatus.BIOMETRIC
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        navigation = session,
                        status = SplashStatus.NON_BIOMETRIC
                    )
                }
            }


        }

    }


}