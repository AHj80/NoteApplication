package com.ahj.noteapplication.core.common.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomAnimateVisibility(
    state: Boolean,
    modifier: Modifier = Modifier,
    enter: EnterTransition = fadeIn(tween(600)),
    exit: ExitTransition = fadeOut(animationSpec = tween(600)),
    content: @Composable AnimatedVisibilityScope.(Transition<EnterExitState>) -> Unit
) {

    AnimatedVisibility(
        visible = state,
        modifier = modifier,
        enter = enter,
        exit = exit,
    ) {
        content(transition)

    }
}