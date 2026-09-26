package com.ahj.noteapplication.feature.authentication.component


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> CustomAnimatedContent(
    state: T,
    modifier: Modifier = Modifier,
    delay: Int = 200,
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = state,
        modifier = modifier,
        transitionSpec = {
            fadeIn(animationSpec = tween(500, delayMillis = delay)) togetherWith (
                    fadeOut(animationSpec = tween(500))
                    )
        },
        label = "StatusAnimation"
    ) {
        content(it)
    }


}