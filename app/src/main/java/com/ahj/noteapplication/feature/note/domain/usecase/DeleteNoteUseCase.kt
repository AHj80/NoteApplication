package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject


class DeleteNoteUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote
) {

    suspend operator fun invoke(note: Note): Boolean{
        return repositoryNote.deleteNote(note)
    }
}