package com.ahj.noteapplication.feature.authentication.presentations.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.R
import com.ahj.noteapplication.app.navigation.Screens


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigation) {

        uiState.navigation?.let {
            if (it) {
                navController.navigate(Screens.ListNoteScreens) {
                    popUpTo<Screens.SplashScreen> {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Screens.Authentication) {
                    popUpTo<Screens.SplashScreen> {
                        inclusive = true
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.onBackground
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
            Image(
                painter = painterResource(R.drawable.icon_result),
                null,
                modifier = Modifier.size(150.dp)

            )
    }
}