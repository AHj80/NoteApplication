package com.ahj.noteapplication.app.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.ahj.noteapplication.app.navigation.SetUi
import com.ahj.noteapplication.core.common.ui.designSystem.NoteApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
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