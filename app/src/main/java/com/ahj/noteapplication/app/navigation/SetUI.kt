package com.ahj.noteapplication.app.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ahj.noteapplication.app.base.MainUiEvent
import com.ahj.noteapplication.app.base.MainViewModel
import com.ahj.noteapplication.core.common.ui.component.BackgroundScreen
import com.ahj.noteapplication.feature.authentication.presentations.auth.AuthenticationScreen
import com.ahj.noteapplication.feature.authentication.presentations.changePass.ChangePasswordScreen
import com.ahj.noteapplication.feature.authentication.presentations.forgetPass.ForgetPassScreen
import com.ahj.noteapplication.feature.authentication.presentations.splash.SplashScreen
import com.ahj.noteapplication.feature.authentication.presentations.verification.VerificationScreen
import com.ahj.noteapplication.feature.note.presentation.createNote.CreateNoteScreen
import com.ahj.noteapplication.feature.note.presentation.noteList.ListNoteScreen

@Composable
fun SetUi(
    viewModel: MainViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDes = backStackEntry?.destination
    val isAuthGraph = currentDes?.hierarchy?.any { it.hasRoute<AuthenticationGraph>() } == true

    LaunchedEffect(currentDes) {

        viewModel.navigating.collect { event ->
            if (!isAuthGraph) {
                if (event is MainUiEvent.NavigateToLogin) {
                    navController.navigate(Screens.Authentication) {
                        popUpTo<Screens.CreateScreen> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }

    }


    Scaffold { innerPadding ->


        SharedTransitionLayout {

            NavHost(
                startDestination = AuthenticationGraph,
                navController = navController
            ) {


                navigation<AuthenticationGraph>(
                    startDestination = Screens.SplashScreen,
                    enterTransition = { enterTransition },
                    exitTransition = { exitTransition },
                    popEnterTransition = { enterTransition },
                    popExitTransition = { exitTransition }

                ) {

                    composable<Screens.SplashScreen>(
                        enterTransition = { fadeIn(animationSpec = tween(durationMillis = 2000)) },
                        exitTransition = { fadeOut(animationSpec = tween(durationMillis = 2000)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(durationMillis = 2000)) },
                        popExitTransition = { fadeOut(animationSpec = tween(durationMillis = 2000)) }
                    ) {
                        SplashScreen(navController)
                    }

                    composable<Screens.Authentication>(
                        enterTransition = { expandVertically(tween(500)) + fadeIn(tween(1000)) },
                    ) {
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

                    composable<Screens.ChangePassScreen> {
                        BackgroundScreen(paddingValues = innerPadding) {

                            ChangePasswordScreen(navController)
                        }

                    }
                }


                navigation<MainGraph>(
                    startDestination = Screens.ListNoteScreens
                ) {

                    composable<Screens.ListNoteScreens> {

                        CompositionLocalProvider(
                            LocalSharedTransitionScope provides this@SharedTransitionLayout,
                            LocalNavAnimateVisibilityScope provides this,
                        ) {

                            BackgroundScreen(paddingValues = innerPadding) {
                                ListNoteScreen(navController)
                            }


                        }
                    }

                    composable<Screens.CreateScreen> {
                        CompositionLocalProvider(
                            LocalSharedTransitionScope provides this@SharedTransitionLayout,
                            LocalNavAnimateVisibilityScope provides this,
                        ) {
                            BackgroundScreen(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding())) {
                                CreateNoteScreen(navController)
                            }
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

private const val TRANSITION_DURATION = 350

// ورود به صفحه (Enter)
private val enterTransition = slideInVertically(
    initialOffsetY = { fullHeight -> fullHeight / 4 },
    animationSpec = tween(
        durationMillis = TRANSITION_DURATION,

        )
) + fadeIn(
    animationSpec = tween(
        durationMillis = TRANSITION_DURATION
    )
)

// خروج از صفحه (Exit)
private val exitTransition = slideOutVertically(
    targetOffsetY = { fullHeight -> -fullHeight / 4 }, 
    animationSpec = tween(
        durationMillis = TRANSITION_DURATION,
        easing = FastOutSlowInEasing
    )
) + fadeOut(
    animationSpec = tween(
        durationMillis = TRANSITION_DURATION
    )
)