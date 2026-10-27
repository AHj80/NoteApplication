package com.ahj.noteapplication.feature.note.data.mapper

import com.ahj.noteapplication.feature.note.data.local.db.entities.NoteEntity
import com.ahj.noteapplication.feature.note.data.local.remote.dto.NoteDto
import com.ahj.noteapplication.feature.note.domain.model.Note


fun NoteEntity.toDomain(): Note =
    Note(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        fontSize = this.fontSize,
        synced = this.synced
    )

fun Note.toEntity(sync: Boolean , userId: String ): NoteEntity =
    NoteEntity(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        fontSize = this.fontSize,
        synced = sync,
        userId = userId
    )

fun NoteEntity.toDto(): NoteDto =
    NoteDto(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        font = this.fontSize,
        userId = this.userId
    )

fun NoteDto.toEntity(): NoteEntity =
    NoteEntity(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        fontSize = this.font,
        userId = this.userId,
        synced = true
    )

fun Note.toDto(userId: String): NoteDto =
    NoteDto(
        id = this.id,
        title = this.title,
        body = this.body,
        date = this.date,
        font = this.fontSize,
        userId = userId
    )
