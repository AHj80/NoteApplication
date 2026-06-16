package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject


class UpdateNoteUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote
) {

    suspend operator fun invoke(
        id: Int,
        title: String,
        body: String,
        fontSize: Int,
        date: String
    ): Boolean {
        return repositoryNote.updateData(id, title, body, fontSize, date)
    }
}