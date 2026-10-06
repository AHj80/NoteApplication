package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject


class UpdateNoteUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote
) {

    suspend operator fun invoke(
        note: Note
    ): Result<Unit> {
        return repositoryNote.updateData(note)
    }
}