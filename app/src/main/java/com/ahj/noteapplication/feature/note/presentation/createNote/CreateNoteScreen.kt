package com.ahj.noteapplication.feature.note.presentation.createNote

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.core.common.ui.component.CreateRichTextField
import com.ahj.noteapplication.core.common.ui.component.CustomAlertDialog
import com.ahj.noteapplication.core.common.ui.component.CustomAnimateVisibility
import com.ahj.noteapplication.core.common.ui.component.CustomProgress
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.mohamedrejeb.richeditor.model.RichTextState


@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: CreateNoteViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    GlassCard(
        corner = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .imePadding()
    ) {

        Box {

            Column {
                TextTitle(uiState.titleState)
                TextBody(uiState.bodyState, size = uiState.stateFont, Modifier.weight(1f))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            bottomBarHeight = with(density) { coordinates.size.height.toDp() }

                        }
                ) {

                    ToolsCreateNote(uiState.bodyState) { onEvent(it) }
                    SpacerHeight(5)

                    BottomItem(
                        backStack = { navController.popBackStack() }) { onEvent(it) }
                }
            }

            CustomAnimateVisibility(
                state = uiState.selectingFontSize,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = bottomBarHeight)
            ) {
                FontItem(uiState.fontList, uiState.stateFont) {
                    onEvent(
                        it
                    )
                }
            }
        }
    }

    CustomAnimateVisibility(uiState.status == CreateNoteStatus.LOADING) {
        CustomProgress()
    }

    CustomAlertDialog(
        uiState.message,
        uiState.dialog
    ) { onEvent(CreateNoteUiEvent.CloseAlertDialog) }

}


@Composable
fun TextTitle(
    title: RichTextState
) {

    CreateRichTextField(
        title,
        "عنوان یادداشت",
        placeholderSize = 25,
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.titleLarge.copy(
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        containerColor = Color.Transparent
    )

}


@Composable
fun TextBody(
    body: RichTextState,
    size: Int,
    modifier: Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {

        item {

            CreateRichTextField(
                body,
                "متن یادداشت...",
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = size.sp
                ),
                containerColor = Color.Transparent,
                modifier = Modifier.fillParentMaxSize()
            )
        }
    }

}

@Composable
fun InsertNoteAction(
    text: String,
    container: Color,
    textColor: Color,
    border: BorderStroke? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        { onClick() },
        colors = ButtonDefaults.buttonColors(container),
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 10.dp),
        border = border,
        enabled = enabled

    ) {
        Text(
            text,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            color = textColor
        )
    }
}

@Composable
private fun ToolsCreateNote(
    richText: RichTextState,
    onEvent: (CreateNoteUiEvent) -> Unit
) {
    val aiColor = Brush.linearGradient(
        listOf(
            Color(0xFF0B2AF5),
            Color(0xFFEC4899),
            Color(0xFF8B5CF6)
        )
    )
    val listIcons = listOf(
        Icons.Default.FormatBold,
        Icons.Default.FormatItalic,
        Icons.Default.FormatUnderlined,
        Icons.Default.FormatSize,
    )



    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            {

            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(brush = aiColor)
                .size(35.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
            ),
        ) {

            Text(
                "AI",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )

        }

        listIcons.forEachIndexed { index, icon ->
            IconButton({
                when (index) {
                    0 -> onEvent(CreateNoteUiEvent.BoldText(richText))
                    1 -> onEvent(CreateNoteUiEvent.ItalicText(richText))
                    2 -> onEvent(CreateNoteUiEvent.UnderlineText(richText))
                    3 -> onEvent(CreateNoteUiEvent.SelectingFontSize(true))
                }
            }) {
                Icon(
                    icon,
                    icon.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }


    }
}


@Composable
private fun FontItem(
    fontList: List<Int>,
    currentFont: Int,
    onEvent: (CreateNoteUiEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onEvent(CreateNoteUiEvent.SelectingFontSize(false))
            },
        contentAlignment = Alignment.BottomEnd
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            fontList.forEach { font ->
                TextButton(
                    onClick = { onEvent(CreateNoteUiEvent.EditFontSize(font)) },
                    modifier = Modifier.padding(3.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            font.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = font.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        if (currentFont == font)
                            HorizontalDivider(
                                modifier = Modifier.width(30.dp),
                                thickness = 4.dp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                    }
                }
            }
        }
    }

}


@Composable
private fun BottomItem(
    enabled: Boolean = true,
    backStack: () -> Unit,
    onEvent: (CreateNoteUiEvent) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InsertNoteAction(
            "ذخیره",
            MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.secondary,
            enabled = enabled
        ) { onEvent(CreateNoteUiEvent.SaveNote) }

        InsertNoteAction(
            "برگشت",
            Color.Transparent,
            textColor = MaterialTheme.colorScheme.primary,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) { backStack() }
    }
}