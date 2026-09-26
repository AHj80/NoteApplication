package com.ahj.noteapplication.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ahj.noteapplication.R

@Composable
fun BackgroundScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues  ,
    content: @Composable () -> Unit
) {

    Box(modifier) {
        Image(
            painter = if (isSystemInDarkTheme()) {
                painterResource(R.drawable.dark_background)
            } else painterResource(
                R.drawable.light_background
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }

}