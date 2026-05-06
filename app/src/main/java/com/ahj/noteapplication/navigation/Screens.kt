package com.ahj.noteapplication.navigation


sealed class Screens(val route: String) {

    object ListNote : Screens("ListNote")
    object CreateNote : Screens("CreateNote")


    fun key(vararg arg: String) =
        buildString {
            append(route)
            arg.forEachIndexed { index, value ->
                append("?$index={$index}")

            }
        }


    fun value(vararg arg: String) =
        buildString {
            append(route)
            arg.forEachIndexed { index, value ->
                append("?$index=$value")
            }
        }

}