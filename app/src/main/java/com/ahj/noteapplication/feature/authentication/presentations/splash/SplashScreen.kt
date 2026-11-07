package com.ahj.noteapplication.feature.authentication.presentations.splash

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.R
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.core.common.utils.biometricHelper


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val onEvent = viewModel::onEvent
    val navigateToListNote: () -> Unit = {
        navController.navigate(Screens.ListNoteScreens) {
            popUpTo<Screens.SplashScreen> {
                inclusive = true
            }
        }
    }

    LaunchedEffect(uiState.navigation) {

        uiState.navigation?.let { session ->
            if (session) {
                when (uiState.status) {
                    SplashStatus.BIOMETRIC -> biometricChecking(
                        activity,
                        context
                    ) {
                        navigateToListNote()
                        onEvent(SplashUiEvent.NavigatingHandled)
                    }


                    SplashStatus.NON_BIOMETRIC -> {
                        navigateToListNote()
                        onEvent(SplashUiEvent.NavigatingHandled)
                    }

                }

            } else {
                navController.navigate(Screens.Authentication) {
                    popUpTo<Screens.SplashScreen> {
                        inclusive = true
                    }
                }
                onEvent(SplashUiEvent.NavigatingHandled)
            }
        }

    }



    Column(
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.icon_result),
            null,
            modifier = Modifier.size(150.dp)
        )
        SpacerHeight(20)
        if (uiState.status == SplashStatus.BIOMETRIC) {
            IconButton(
                {
                    biometricChecking(
                        activity,
                        context,

                        ) {
                        navigateToListNote()
                        onEvent(SplashUiEvent.NavigatingHandled)
                    }
                },
                modifier = Modifier.scale(2f)
            ) {
                Icon(
                    Icons.Default.Fingerprint,
                    null,
                    tint = MaterialTheme.colorScheme.primary,

                    )
            }
        }
    }

}


private fun biometricChecking(
    activity: FragmentActivity?,
    context: Context,
    onSuccess: () -> Unit,
) {

    activity?.let { fragmentActivity ->
        biometricHelper(
            fragmentActivity,
            "هویت خود را تایید نمایید",
            onSuccess
        ) {
            Toast.makeText(
                context,
                "احراز اثرانگشت ناموفق بود!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

