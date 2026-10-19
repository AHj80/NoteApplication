package com.ahj.noteapplication.app.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ahj.noteapplication.core.common.ui.component.BackgroundScreen
import com.ahj.noteapplication.feature.authentication.presentations.auth.AuthenticationScreen
import com.ahj.noteapplication.feature.authentication.presentations.forgetPass.ForgetPassScreen
import com.ahj.noteapplication.feature.authentication.presentations.splash.SplashScreen
import com.ahj.noteapplication.feature.authentication.presentations.verification.VerificationScreen
import com.ahj.noteapplication.feature.note.presentation.createNote.CreateNoteScreen
import com.ahj.noteapplication.feature.note.presentation.noteList.ListNoteScreen

@Composable
fun SetUi() {

    val navController = rememberNavController()

    Scaffold { innerPadding ->


        SharedTransitionLayout {

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
                    BackgroundScreen(paddingValues = innerPadding) {
                        ForgetPassScreen(navController)
                    }
                }

                composable<Screens.VerificationScreen> {
                    BackgroundScreen(paddingValues = innerPadding) {
                        VerificationScreen(navController = navController)
                    }
                }

                composable<Screens.ListNoteScreens>(
                    enterTransition = { enterTransition },
                    exitTransition = { exitTransition }
                ) {

                    CompositionLocalProvider(
                        LocalSharedTransitionScope provides this@SharedTransitionLayout,
                        LocalNavAnimateVisibilityScope provides this,
                    ) {
                        BackgroundScreen(paddingValues = innerPadding) {
                            ListNoteScreen(navController)

                        }
                    }
                }

                composable<Screens.CreateScreen>(
                    enterTransition = { enterTransition },
                    exitTransition = { exitTransition }
                ) {

                    val input = it.toRoute<Screens.CreateScreen>()
                    CompositionLocalProvider(
                        LocalSharedTransitionScope provides this@SharedTransitionLayout,
                        LocalNavAnimateVisibilityScope provides this,
                    ) {
                        BackgroundScreen(paddingValues = innerPadding) {
                            CreateNoteScreen(navController, input.id)
                        }
                    }
                }

            }
        }
    }


}

val LocalSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope> {
    error("SharedTransitionScope not found")
}

val LocalNavAnimateVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope> {
    error("AnimateVisibilityScope not found")
}

private val enterTransition = fadeIn(tween(1000))
private val exitTransition = fadeOut(tween(10800))
