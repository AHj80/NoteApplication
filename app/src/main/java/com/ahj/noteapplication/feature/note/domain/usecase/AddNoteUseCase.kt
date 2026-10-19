package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import java.util.UUID
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repositoryNote: RepositoryNote
) {


    suspend operator fun invoke(
        title: String,
        body: String,
        date: String,
        fontSize: Int
    ): Result<String> {
        val id = UUID.randomUUID().toString()

        return if (title.isEmpty())
            Result.failure(IllegalArgumentException("Data is Empty"))
        else
            repositoryNote.addNote(
                Note(
                    id = id,
                    title = title,
                    body = body,
                    date = date,
                    fontSize = fontSize
                )
            ).map {
                id
            }
    }

}