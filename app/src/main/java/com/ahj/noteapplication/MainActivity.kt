package com.ahj.noteapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ahj.noteapplication.navigation.Setup
import com.ahj.noteapplication.ui.theme.NoteApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NoteApplicationTheme {

                Setup()
            }
        }
    }


}

