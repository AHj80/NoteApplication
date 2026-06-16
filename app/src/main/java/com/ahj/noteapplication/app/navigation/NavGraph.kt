package com.ahj.noteapplication.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahj.noteapplication.feature.note.presentation.note_creation.CreateNoteScreen
import com.ahj.noteapplication.feature.note.presentation.note_list.ListNoteScreen

@Composable
fun SetUi(){

    val navController = rememberNavController()

    NavHost(
        startDestination = Screens.ListNoteScreens,
        navController = navController,
    ){
        composable<Screens.ListNoteScreens> {
            ListNoteScreen(navController)
        }
        composable<Screens.CreateScreen>(
            enterTransition = {
                slideInVertically(initialOffsetY = { it }) + fadeIn(tween (durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { -it }) + fadeOut(tween (durationMillis = 500))
            },
            popEnterTransition = {
                slideInVertically(initialOffsetY = { -it }) + fadeIn(tween (durationMillis = 500))
            },

            popExitTransition = {
                slideOutVertically(targetOffsetY = { it }) + fadeOut(tween (durationMillis = 500))
            }
        ) {

            val data = it.toRoute<Screens.CreateScreen>()
            CreateNoteScreen(navController, id = data.id)
        }
    }

}