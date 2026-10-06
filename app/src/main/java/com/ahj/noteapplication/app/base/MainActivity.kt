package com.ahj.noteapplication.app.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.app.navigation.SetUi
import com.ahj.noteapplication.core.common.ui.component.BackgroundScreen
import com.ahj.noteapplication.core.common.ui.designSystem.NoteApplicationTheme
import com.ahj.noteapplication.feature.note.presentation.createNote.CreateNoteScreen
import com.ahj.noteapplication.feature.note.presentation.noteList.ListNoteScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            NoteApplicationTheme {

                SetUi()
            }
        }
    }
}