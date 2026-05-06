package com.ahj.noteapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahj.noteapplication.view.CreateNoteScreen
import com.ahj.noteapplication.view.ListNoteScreen

@Composable
fun Setup() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screens.ListNote.route
    ) {

        composable(
            route = Screens.ListNote.route
        ) {
            ListNoteScreen(navController )
        }

        composable(
            route = Screens.CreateNote.key("0 -> id"),
            arguments = listOf(
                navArgument("0"){
                    type = NavType.StringType
                    defaultValue = "0"
                }
            )

        ) {
            val id = it.arguments?.getString("0")?:"0"
            CreateNoteScreen(navController = navController , id = id.toInt())

        }
    }

}