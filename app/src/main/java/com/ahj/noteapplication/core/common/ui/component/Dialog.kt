package com.ahj.noteapplication.core.common.ui.component


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahj.noteapplication.feature.authentication.component.InsertAuthButton


@Composable
fun CustomAlertDialog(text: String?, state: Boolean, onDismiss: () -> Unit) {

    CustomAnimateVisibility(state = state) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onDismiss()
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                elevation = CardDefaults.cardElevation(6.dp),
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 30.dp)
                ) {
                    text?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    InsertAuthButton("بستن", true) {
                        onDismiss()
                    }
                }

            }
        }
    }

}