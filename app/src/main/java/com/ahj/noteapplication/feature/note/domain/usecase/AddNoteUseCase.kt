package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import java.util.UUID
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote
) {

    private val _id = UUID.randomUUID().toString()
    suspend operator fun invoke(
        title: String,
        body: String,
        date: String,
        fontSize: Int
        ): Result<String> {



        return if (title.isEmpty())
            Result.failure(IllegalArgumentException("Data is Empty"))
        else
            repositoryNote.addNote(
                Note(
                    id = _id ,
                    title =title,
                    body = body,
                    date = date,
                    fontSize = fontSize
                )
            ).map {
                _id
            }
    }

}