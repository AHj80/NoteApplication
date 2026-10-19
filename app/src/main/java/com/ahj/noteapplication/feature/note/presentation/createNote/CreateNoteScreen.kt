package com.ahj.noteapplication.feature.note.presentation.createNote

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ContentPasteGo
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.core.common.ui.component.CreateResultScreen
import com.ahj.noteapplication.core.common.ui.component.CreateRichTextField
import com.ahj.noteapplication.core.common.ui.component.CustomAlertDialog
import com.ahj.noteapplication.core.common.ui.component.CustomAnimateVisibility
import com.ahj.noteapplication.core.common.ui.component.CustomProgress
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.feature.authentication.component.CustomAnimatedContent
import com.ahj.noteapplication.feature.note.component.CustomModalGlass
import com.ahj.noteapplication.feature.note.domain.model.NameTools
import com.ahj.noteapplication.feature.note.domain.model.TextActions
import com.ahj.noteapplication.feature.note.domain.model.ToolsButton
import com.ahj.noteapplication.feature.note.domain.model.UserActionAI
import com.ahj.noteapplication.feature.note.presentation.createNote.ai.AIStatus
import com.ahj.noteapplication.feature.note.presentation.createNote.ai.AIUiEvent
import com.ahj.noteapplication.feature.note.presentation.createNote.ai.AIViewModel
import com.ahj.noteapplication.feature.note.presentation.createNote.create.CreateNoteStatus
import com.ahj.noteapplication.feature.note.presentation.createNote.create.CreateNoteUiEvent
import com.ahj.noteapplication.feature.note.presentation.createNote.create.CreateNoteViewModel
import com.mohamedrejeb.richeditor.model.RichTextState


@Composable
fun CreateNoteScreen(
    navController: NavController,
    createNoteViewModel: CreateNoteViewModel = hiltViewModel(),
    aiViewModel: AIViewModel = hiltViewModel()
) {

    val createUiState by createNoteViewModel.uiState.collectAsStateWithLifecycle()
    val aiUiState by aiViewModel.uiState.collectAsStateWithLifecycle()
    val createOnEvent = createNoteViewModel::onEvent


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
                TextTitle(createUiState.titleState)
                TextBody(
                    createUiState.bodyState,
                    size = createUiState.stateFont,
                    Modifier.weight(1f)
                )
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

                    ToolsCreateNote(createUiState.bodyState) { createOnEvent(it) }
                    SpacerHeight(5)

                    BottomItem(
                        backStack = { navController.popBackStack() }) { createOnEvent(it) }
                }
            }

            CustomAnimateVisibility(
                state = createUiState.selectingFontSize,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = bottomBarHeight)
            ) {
                FontItem(createUiState.fontList, createUiState.stateFont) {
                    createOnEvent(
                        it
                    )
                }
            }
        }
    }

    CustomAnimateVisibility(createUiState.status == CreateNoteStatus.LOADING) {
        CustomProgress()
    }


    AIModalBottom(
        createUiState.aiBottomSheet,
        aiUiState.aiResponse,
        createUiState.bodyState.annotatedString.text,
        aiUiState.status,
        onDismiss = { createOnEvent(CreateNoteUiEvent.ChangeModalAI(false)) },
        addTextAI = { createOnEvent(CreateNoteUiEvent.SetAIResponse(it)) }
    ) { aiViewModel.onEvent(it) }

    CustomAlertDialog(
        createUiState.message,
        createUiState.dialog
    ) { createOnEvent(CreateNoteUiEvent.CloseAlertDialog) }


}


@Composable
private fun TextTitle(
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
private fun TextBody(
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
                    fontSize = size.sp,
                    lineHeight = (size * 1.5).sp
                ),
                containerColor = Color.Transparent,
                modifier = Modifier.fillParentMaxSize()
            )
        }
    }

}

@Composable
private fun InsertNoteAction(
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
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val aiColor = Brush.linearGradient(
        listOf(
            Color(0xFF0B2AF5),
            Color(0xFFEC4899),
            Color(0xFF8B5CF6)
        )
    )
    val listIcons = listOf(
        ToolsButton(
            NameTools.BOLD,
            Icons.Default.FormatBold
        ),
        ToolsButton(
            NameTools.ITALIC,
            Icons.Default.FormatItalic
        ),
        ToolsButton(
            NameTools.UNDERLINE,
            Icons.Default.FormatUnderlined,
        ),
        ToolsButton(
            NameTools.FONT_SIZE,
            Icons.Default.FormatSize,
        ),
    )


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            {
                focusManager.clearFocus()
                keyboardController?.hide()
                onEvent(CreateNoteUiEvent.ChangeModalAI(true))
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(brush = aiColor)
                .size(35.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
            ),

            enabled = richText.annotatedString.text.isNotBlank()
        ) {
            Text(
                "AI",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }

        listIcons.forEach { tools ->

            val isSelected = when (tools.name) {
                NameTools.BOLD -> richText.currentSpanStyle.fontWeight == FontWeight.ExtraBold
                NameTools.ITALIC -> richText.currentSpanStyle.fontStyle == FontStyle.Italic
                NameTools.UNDERLINE -> richText.currentSpanStyle.textDecoration == TextDecoration.Underline
                NameTools.FONT_SIZE -> false
            }
            IconButton(
                {
                    when (tools.name) {
                        NameTools.BOLD -> onEvent(CreateNoteUiEvent.BoldText(richText))
                        NameTools.ITALIC -> onEvent(CreateNoteUiEvent.ItalicText(richText))
                        NameTools.UNDERLINE -> onEvent(CreateNoteUiEvent.UnderlineText(richText))
                        NameTools.FONT_SIZE -> onEvent(CreateNoteUiEvent.SelectingFontSize(true))
                    }

                }
            ) {
                Icon(
                    tools.icon,
                    "${tools.name}",
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
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


@Composable
private fun AIModalBottom(
    visibility: Boolean,
    aiResponse: String?,
    bodyText: String,
    status: AIStatus,
    onDismiss: () -> Unit,
    addTextAI: (String) -> Unit,
    onEvent: (AIUiEvent) -> Unit
) {

    var processAI by rememberSaveable { mutableStateOf(false) }
    BackHandler(visibility, onBack = onDismiss )

    val promptList = listOf(
        UserActionAI("کوتاه کردن متن", TextActions.SHORT),
        UserActionAI("گسترش متن", TextActions.LONG),
        UserActionAI("ترجمه متن", TextActions.TRANSLATE),
        UserActionAI("حل مسئله ریاضی", TextActions.PROCESS_MATH)
    )



    CustomAnimateVisibility(
        visibility,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight }, // شروع از پایین صفحه (خارج از کادر)
            animationSpec = tween(durationMillis = 400) // تنظیم سرعت انیمیشن (مثلاً 400 میلی‌ثانیه)
        ) + fadeIn(
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight }, // خروج به سمت پایین صفحه
            animationSpec = tween(durationMillis = 400)
        ) + fadeOut(
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        CustomModalGlass(
            modifier = Modifier.fillMaxHeight(0.5f),
            onDismiss
        ) {
            CustomAnimateVisibility(processAI, exit = fadeOut(tween(200))) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        { aiResponse?.let { addTextAI(it) } },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondary),

                        ) {
                        Icon(
                            Icons.Default.ContentPasteGo,
                            null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        { processAI = false },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            }


            CustomAnimatedContent(processAI) { state ->
                if (state) {
                    when (status) {
                        AIStatus.IDLE -> {}
                        AIStatus.LOADING -> {
                            CustomProgress()
                        }

                        AIStatus.SUCCESS -> {
                            aiResponse?.let {
                                AIResponse(aiResponse)
                            }
                        }

                        AIStatus.ERROR -> {
                            CreateResultScreen(aiResponse)
                        }
                    }

                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        promptList.forEach { prompt ->
                            SpacerHeight(20)

                            InsertNoteAction(
                                prompt.prompt,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.primary
                            ) {
                                onEvent(AIUiEvent.Action(bodyText, prompt.action))
                                processAI = true
                            }

                        }
                    }
                }

            }
        }
    }
}


@Composable
private fun AIResponse(
    response: String?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
            .border(
                BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(topStart = 15.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        response?.let {
            Text(
                it,
                style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Rtl),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
        }

    }


}