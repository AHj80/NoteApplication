package com.ahj.noteapplication.app.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ahj.noteapplication.core.common.ui.theme.NoteApplicationTheme
import com.ahj.noteapplication.app.navigation.SetUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NoteApplicationTheme {

                SetUi()
            }
        }
    }


}