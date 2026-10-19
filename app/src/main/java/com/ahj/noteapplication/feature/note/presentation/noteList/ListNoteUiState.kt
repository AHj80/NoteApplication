package com.ahj.noteapplication.feature.note.presentation.noteList

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.mohamedrejeb.richeditor.model.RichTextState

data class ListNoteUiState(
    val note: List<Note> = emptyList(),
    val statusMessage: String? = null,
    val message: String? = null,
    val stateSearching: RichTextState = RichTextState(),
    val status: ListScreenStatus = ListScreenStatus.EMPTY,
    val windowHolding: Boolean = false,
    val selectedNote: String? = null,
    val stateDialog: Boolean = false,
    val isUnsynced: Boolean = false
) {

    val searchNote: List<Note>
        get() {
            val searchQuery = stateSearching.annotatedString.text
            return if (searchQuery.isBlank())
                note
            else
                note.filter { it.title.contains(searchQuery) || it.body.contains(searchQuery) }

        }

    val listStatusScreen: ListScreenStatus
        get() {
            return when {
                status == ListScreenStatus.LOADING -> ListScreenStatus.LOADING
                statusMessage != null && status == ListScreenStatus.ERROR -> ListScreenStatus.ERROR
                status == ListScreenStatus.EMPTY -> ListScreenStatus.EMPTY
                searchNote.isEmpty() -> ListScreenStatus.NOTE_FOUND
                else -> ListScreenStatus.SUCCESS
            }
        }
}

