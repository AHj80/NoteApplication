package com.ahj.noteapplication.feature.authentication.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InsertAuthButton(
    text: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        { onClick() },
        modifier = Modifier

            .shadow(
                10.dp,
                shape = CircleShape,
                spotColor = Color.White,
                ambientColor = MaterialTheme.colorScheme.primary
            ),
        enabled = enabled,

        ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            modifier = modifier
        )
    }
}