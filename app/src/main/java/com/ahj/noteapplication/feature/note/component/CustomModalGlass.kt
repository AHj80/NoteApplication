package com.ahj.noteapplication.feature.note.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight


@Composable
fun CustomModalGlass(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {

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
            GlassCard(
                corner = RoundedCornerShape(topStart = 40.dp, 40.dp),
                padding = 0,
            ) {
                SpacerHeight(20)
                Row(
                    modifier = Modifier.fillMaxWidth(),
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