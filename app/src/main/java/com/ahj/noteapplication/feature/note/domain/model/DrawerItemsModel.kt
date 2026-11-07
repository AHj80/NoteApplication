package com.ahj.noteapplication.feature.note.domain.model

data class DrawerItemsModel(
    val id: DrawerItems,
    val title: String
)


enum class DrawerItems {
    CHANGE_PASS,
    CONTACTS,
    UPDATE,
    LOGOUT
}