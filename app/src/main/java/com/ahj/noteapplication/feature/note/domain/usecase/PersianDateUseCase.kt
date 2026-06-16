package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject

class PersianDateUseCase @Inject constructor(
  private val repositoryNote: RepositoryNote
)  {
    operator fun invoke(): String{

        return repositoryNote.showDate()
    }
}