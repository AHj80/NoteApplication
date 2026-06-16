package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject


class GetNoteByIdUseCase @Inject constructor(
     private val repositoryNote: RepositoryNote
) {

    suspend operator fun invoke(id: Int) = repositoryNote.getNoteById(id)

}