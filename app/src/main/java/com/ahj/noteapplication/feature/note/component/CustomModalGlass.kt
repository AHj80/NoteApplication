package com.ahj.noteapplication.feature.note.component

import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight

enum class DragValue {
    Expanded, // حالت باز
    Dismissed // حالت بسته
}

@Composable
fun CustomModalGlass(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

    val state = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Expanded,
            anchors = DraggableAnchors {
                DragValue.Expanded at 0f          // موقعیت اولیه
                DragValue.Dismissed at 1000f      // موقعیت بسته شدن (انتهای صفحه)
            },
        )
    }

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
                    .clip(RoundedCornerShape(topStart = 40.dp, 40.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.9f),
                ),
                border = BorderStroke(2.dp, color = Color.White.copy(alpha = 0.3f)),
            ) {
                SpacerHeight(20)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .anchoredDraggable(
                                state = state,
                                orientation = Orientation.Vertical
                            )
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