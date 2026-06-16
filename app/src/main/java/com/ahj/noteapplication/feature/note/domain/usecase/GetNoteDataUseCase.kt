package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetNoteDataUseCase @Inject constructor(

    private val repositoryNote: RepositoryNote
) {

    operator fun invoke(): Flow<List<Note>>{
        return repositoryNote.getNoteData()

    }
}