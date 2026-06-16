package com.ahj.noteapplication.feature.note.data.mapper

import com.ahj.noteapplication.feature.note.data.local.entities.NoteEntity
import com.ahj.noteapplication.feature.note.domain.model.Note


fun NoteEntity.toDomain(): Note =
    Note(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        fontSize = this.fontSize
    )

fun Note.toEntity(): NoteEntity =
    NoteEntity(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        fontSize = this.fontSize
    )
