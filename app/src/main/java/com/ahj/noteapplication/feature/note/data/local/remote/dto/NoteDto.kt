package com.ahj.noteapplication.feature.note.data.local.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class NoteDto(
    val id: String,
    val userId: String?,
    val title: String = "",
    val body: String = "",
    val date: String = "",
    val font: Int = 16
)