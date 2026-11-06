package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.core.dataStore.SessionManager
import com.ahj.noteapplication.feature.note.domain.model.NoteDataModel
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetNoteDataUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote,
    private val sessionManager: SessionManager
) {

    operator fun invoke(): Flow<NoteDataModel> {
        return combine(
            repositoryNote.getNoteData(),
            repositoryNote.getExistsUnsynced(),
            sessionManager.biometricEnable,
            sessionManager.userEmail()

        ) { notes, exists, biometric, email ->

            NoteDataModel(
                notes,
                exists,
                biometric,
                email
            )
        }

    }
}