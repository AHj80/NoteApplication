package com.ahj.noteapplication.app.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ahj.noteapplication.app.navigation.SetUi
import com.ahj.noteapplication.core.common.ui.component.BackgroundScreen
import com.ahj.noteapplication.core.common.ui.designSystem.NoteApplicationTheme
import com.ahj.noteapplication.feature.authentication.presentations.splash.SplashScreen
import com.ahj.noteapplication.feature.authentication.presentations.verification.VerificationScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NoteApplicationTheme {

                /*SetUi()*/

                Scaffold() {

                    BackgroundScreen(paddingValues = it) {

                    }
                }
            }
        }
    }


}