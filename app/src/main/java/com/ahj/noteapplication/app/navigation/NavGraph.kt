package com.ahj.noteapplication.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahj.noteapplication.core.common.ui.component.BackgroundScreen
import com.ahj.noteapplication.feature.authentication.presentations.auth.AuthenticationScreen
import com.ahj.noteapplication.feature.authentication.presentations.splash.SplashScreen
import com.ahj.noteapplication.feature.authentication.presentations.verification.VerificationScreen
import com.ahj.noteapplication.feature.note.presentation.note_creation.CreateNoteScreen
import com.ahj.noteapplication.feature.note.presentation.note_list.ListNoteScreen

@Composable
fun SetUi() {

    val navController = rememberNavController()

    Scaffold { innerPadding ->


        NavHost(
            startDestination = Screens.SplashScreen,
            navController = navController
        ) {

            composable<Screens.SplashScreen> {
                SplashScreen(navController)
            }

            composable<Screens.Authentication> {
                BackgroundScreen(paddingValues = innerPadding) {
                    AuthenticationScreen(navController)
                }
            }

            composable<Screens.ForgetPassword> {

            }

            composable<Screens.VerificationScreen> {
                BackgroundScreen(paddingValues = innerPadding) {
                    VerificationScreen(navController = navController)
                }
            }

            composable<Screens.ListNoteScreens> {
                BackgroundScreen(paddingValues = innerPadding) {


                    ListNoteScreen(navController)

                }
            }

            composable<Screens.CreateScreen>(
                enterTransition = {
                    slideInVertically(initialOffsetY = { it }) + fadeIn(tween(durationMillis = 500))
                },
                exitTransition = {
                    slideOutVertically(targetOffsetY = { -it }) + fadeOut(tween(durationMillis = 500))
                },
                popEnterTransition = {
                    slideInVertically(initialOffsetY = { -it }) + fadeIn(tween(durationMillis = 500))
                },

                popExitTransition = {
                    slideOutVertically(targetOffsetY = { it }) + fadeOut(tween(durationMillis = 500))
                }
            ) {

                val data = it.toRoute<Screens.CreateScreen>()
                BackgroundScreen(paddingValues = innerPadding) {
                    CreateNoteScreen(navController, id = data.id)

                }
            }
        }
    }


}