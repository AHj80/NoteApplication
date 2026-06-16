package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote
) {

    suspend operator fun invoke(note: Note): Result<Long>{


        return if (note.title.isEmpty())
            Result.failure(IllegalArgumentException("Data is Empty"))
        else
            repositoryNote.addNote(note)
    }

}