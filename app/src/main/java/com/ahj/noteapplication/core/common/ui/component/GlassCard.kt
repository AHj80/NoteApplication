package com.ahj.noteapplication.core.common.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    corner: RoundedCornerShape = RoundedCornerShape(0.dp),
    padding: Int = 20,
    glassClick: (() -> Unit)? = null,
    glassHolder: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    Card(
        shape = corner,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = padding.dp)
            .clip(corner)
            .then(
                if (glassHolder != null || glassClick != null) {
                    Modifier.combinedClickable(
                        onClick = { glassClick?.invoke() },
                        onLongClick = { glassHolder?.invoke() },
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        border = BorderStroke(2.dp, color = Color.White.copy(alpha = 0.3f)),
    ) {
        content()
    }

}