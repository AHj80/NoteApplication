package com.ahj.noteapplication.feature.note.presentation.noteList

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.ui.component.BackgroundScreen
import com.ahj.noteapplication.core.common.ui.component.CreateResultScreen
import com.ahj.noteapplication.core.common.ui.component.CreateRichTextField
import com.ahj.noteapplication.core.common.ui.component.CreateTextField
import com.ahj.noteapplication.core.common.ui.component.CustomAnimateVisibility
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlin.math.min


@Composable
fun ListNoteScreen(
    navController: NavController,
    viewModel: ListNoteViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery = uiState.stateSearching.annotatedString.text
    val searching by remember(searchQuery, uiState.note) {
        derivedStateOf {
            if (searchQuery.isBlank())
                uiState.note
            else uiState.note.filter {
                it.title.contains(searchQuery)
            }
        }
    }

    val screenStatus = when {
        uiState.note.isEmpty() -> ListScreenStatus.EMPTY

        searching.isEmpty() -> ListScreenStatus.NOTE_FOUND

        uiState.message != null -> ListScreenStatus.ERROR
        else -> ListScreenStatus.SUCCESS
    }



    Scaffold(
        topBar = { NoteTopBar(uiState.stateSearching) },
    ) { innerPadding ->


        BackgroundScreen(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding())) {

            AnimatedContent(
                targetState = screenStatus,
                transitionSpec = {
                    fadeIn(animationSpec = tween(delayMillis = 500)) togetherWith
                            fadeOut(animationSpec = tween(delayMillis = 500))
                }
            ) { status ->
                when (status) {

                    ListScreenStatus.EMPTY -> {
                        CreateResultScreen("یادداشتی وجود ندارد.")
                    }


                    ListScreenStatus.ERROR -> {
                        CreateResultScreen(uiState.message)
                    }

                    ListScreenStatus.NOTE_FOUND -> {
                        CreateResultScreen("یادداشتی یافت نشد!")
                    }

                    else -> {
                        SampleNotes(
                            searching
                        ) { event ->
                            if (event is ListNoteUiEvent.Navigating)
                                navController.navigate(Screens.CreateScreen(event.state))
                            else viewModel.onEvent(event)
                        }
                    }


                }


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    BottomBar {
                        if (it is ListNoteUiEvent.Navigating) {
                            navController.navigate(Screens.CreateScreen(it.state))
                        }
                    }
                }

            }

        }


    }

}

@Composable
fun NoteTopBar(
    state: RichTextState
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        Icon(
            Icons.Default.SyncProblem,
            "sync",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(horizontal =  5.dp)
                .size(30.dp)

        )

        CreateRichTextField(
            state = state,
            "جست و جو بر اساس عنوان...",
            leadingIcon = {
                Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary)
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        )

    }

}


@Composable
fun SampleNotes(
    item: List<Note>,
    onEvent: (ListNoteUiEvent) -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(item.size, { item[it].id }) {

            val note = item[it]

            NoteItem(
                note,
                modifier = Modifier.animateItem(
                    fadeInSpec = tween(500),
                    fadeOutSpec = tween(500),
                    placementSpec = spring(stiffness = Spring.StiffnessLow)
                ),
                {
                    onEvent(ListNoteUiEvent.Navigating(note.id))
                },
                { onEvent(ListNoteUiEvent.DeleteNote(note)) }
            )

        }

    }


}

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    glassClick: () -> Unit,
    deleteNote: () -> Unit
) {

    var state by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { state = false },

        ) {
        GlassCard(
            corner = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clip(RoundedCornerShape(20.dp)),
            glassClick = glassClick,
            glassHolder = { state = !state }
        ) {

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        note.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 23.sp,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp)
                            .weight(1f),
                        color = MaterialTheme.colorScheme.primary,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (!note.synced) {
                        Icon(
                            Icons.Default.CloudOff,
                            "not_synced",
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(20.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }


                Text(
                    note.body,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp),
                    modifier = Modifier
                        .padding(10.dp),
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }


        }
        CustomAnimateVisibility(state, Modifier.matchParentSize()) {
            HoldingItem(
                note.date,
                { state = false },
                deleteNote
            )
        }
    }

}


@Composable
fun BottomBar(
    onEvent: (ListNoteUiEvent) -> Unit
) {


    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            { onEvent(ListNoteUiEvent.Navigating("")) },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
            Text(
                "+",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }


    }
}


@Composable
private fun HoldingItem(
    date: String,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismiss() },
        contentAlignment = Alignment.TopEnd
    ) {
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier
                .clickable(false) {}
                .padding(top = 20.dp, end = 40.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {

                IconButton({ onClick() }) {
                    Icon(
                        Icons.Default.DeleteForever,
                        "delete_note",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                Text(
                    date,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

        }
    }
}


