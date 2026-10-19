package com.ahj.noteapplication.feature.note.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ToolsButton(
    val name: NameTools,
    val icon: ImageVector
)

enum class NameTools {
    BOLD,
    FONT_SIZE,
    ITALIC,
    UNDERLINE
}