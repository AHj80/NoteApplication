package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.model.NoteDataModel
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetNoteDataUseCase @Inject constructor(

    private val repositoryNote: RepositoryNote
) {

    operator fun invoke(): Flow<NoteDataModel> {
        return combine(
            repositoryNote.getNoteData(),
            repositoryNote.getExistsUnsynced()
        ) { notes, exists ->
            NoteDataModel(
                notes,
                exists
            )
        }

    }
}