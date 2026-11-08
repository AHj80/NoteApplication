package com.ahj.noteapplication.feature.note.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CustomModalGlass(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

    val offsetY = remember { Animatable(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onDismiss()
            },
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(
            Modifier.clickable(enabled = false) {}
        ) {

            Card(
                shape = RoundedCornerShape(topStart = 40.dp, 40.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .onSizeChanged {
                        height = it.height.toFloat()
                    }
                    .offset {
                        IntOffset(0, offsetY.value.roundToInt())
                    }
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            val newOffset = offsetY.value + delta
                            scope.launch {
                                offsetY.snapTo(newOffset.coerceIn(0f, height))
                            }
                        },
                        onDragStopped = { velocity ->
                            scope.launch {
                                if (offsetY.value > height / 2 || velocity >= 1000f)
                                    onDismiss()
                                else
                                    offsetY.animateTo(
                                        0f,
                                        spring(0.7f, Spring.StiffnessMediumLow)
                                    )
                            }
                        },
                    )
                    .clip(RoundedCornerShape(topStart = 40.dp, 40.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                ),
                border = BorderStroke(2.dp, color = Color.White.copy(alpha = 0.3f)),
            ) {
                SpacerHeight(20)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(50.dp),
                        thickness = 7.dp,
                        color = Color.Gray
                    )
                }

                content()
            }
        }
    }
}
