package com.ahj.noteapplication.feature.note.presentation.createNote.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahj.noteapplication.feature.note.domain.model.TextActions
import com.ahj.noteapplication.feature.note.domain.usecase.TextPromptUseCase
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
class AIViewModel @Inject constructor(
    private val textPromptUseCase: TextPromptUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AIUiState())
    val uiState: StateFlow<AIUiState> = _uiState.asStateFlow()

    fun onEvent(event: AIUiEvent) {

        if (event is AIUiEvent.Action) {
            receiveResponseAI(event.body, event.actions)
        }

    }

    private fun receiveResponseAI(text: String, action: TextActions) {

        viewModelScope.launch {
            _uiState.update { it.copy(status = AIStatus.LOADING, aiResponse = null) }
            textPromptUseCase(text, action)
                .onSuccess { result ->

                    _uiState.update {
                        it.copy(
                            status = AIStatus.SUCCESS,
                            aiResponse = ""
                        )
                    }
                    val stringBuilder = StringBuilder()
                    result.forEach { char ->
                        val response = stringBuilder.append(char).toString()
                        _uiState.update { it.copy(aiResponse = response) }
                        delay(20.milliseconds)

                    }

                }
                .onFailure { error ->
                    _uiState.update { it.copy(status = AIStatus.ERROR, aiResponse = error.message) }
                }
        }

    }

}