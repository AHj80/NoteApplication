package com.ahj.noteapplication.feature.note.presentation.note_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.core.common.ui.component.CreateResultScreen
import com.ahj.noteapplication.core.common.ui.component.CreateTextField
import com.ahj.noteapplication.feature.note.domain.model.Note
import com.ahj.noteapplication.app.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNoteScreen(
    navController: NavController,
    viewModel: ListNoteViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBar = remember { SnackbarHostState() }


    val result = if (state.stateSearching.isBlank())
        state.note
    else
        state.note.filter {
            it.title.contains(
                state.stateSearching,
                ignoreCase = true
            )
        }
    val screenState = when {
        state.note.isEmpty() -> ScreenState.EMPTY
        result.isEmpty() -> ScreenState.NOTE_FOUND
        else -> ScreenState.SUCCESS
    }


    LaunchedEffect(
        state.message
    ) {
        state.message?.let {
            snackBar.showSnackbar(
                it,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            viewModel.cleanMessage()
        }

    }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( start = 5.dp, end = 5.dp , )
                            .size(width = 270.dp, height = 60.dp),
                        elevation = CardDefaults.cardElevation(10.dp)

                    ) {
                        CreateTextField(
                            state.stateSearching,
                            stateTextChanged = { viewModel.updateStateSearch(it) },
                            modifier = Modifier
                                .fillMaxSize(),
                            leadingIcon = Icons.Default.Search,
                            placeholder = "جست و جو بر اساس عنوان:"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                /*modifier = Modifier.height(75.dp)*/
            )


        },
        floatingActionButton = {
            FloatingActionButton(
                { navController.navigate(Screens.CreateScreen(0)) },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.AddCircle,
                    null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.scale(1.8f)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            SnackbarHost(snackBar) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    dismissActionContentColor = MaterialTheme.colorScheme.tertiary
                )
            }

        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            AnimatedContent(
                targetState = screenState ,
                transitionSpec = {
                    fadeIn(animationSpec = tween(delayMillis = 500)) togetherWith
                            fadeOut(animationSpec = tween(delayMillis = 500))
                }
            ) { target ->
                when (target) {
                    ScreenState.EMPTY ->{
                        CreateResultScreen("یادداشتی موجود نیست")
                    }
                    ScreenState.NOTE_FOUND ->{
                        CreateResultScreen("یادداشتی یافت نشد")
                    }
                    ScreenState.SUCCESS -> {
                        LazyVerticalStaggeredGrid(
                            StaggeredGridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalItemSpacing = 3.dp

                        ) {
                            items(result.size, key = { result[it].id }) {
                                val currentNote = result[it]
                                SampleNoteScreen(
                                    currentNote,
                                    actionViewModel = {
                                        navController.navigate(
                                            Screens.CreateScreen(currentNote.id)
                                        )
                                    },
                                    deleteNote = { viewModel.deleteNote(currentNote) },
                                    modifier = Modifier.animateItem(
                                        fadeInSpec = tween(500),
                                        fadeOutSpec = tween(500),
                                        placementSpec = spring(stiffness = Spring.StiffnessLow)
                                    )
                                )

                            }
                        }
                    }
                }
            }


        }

    }


}

@Composable
fun SampleNoteScreen(
    note: Note,
    actionViewModel: () -> Unit,
    deleteNote: () -> Unit,
    modifier: Modifier = Modifier
) {

    val listSize = remember { listOf(250.dp, 190.dp, 200.dp).random() }
    Card(
        modifier = modifier
            .padding(5.dp)
            .width(100.dp)
            .height(listSize)
            .padding(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            topStart = 15.dp,
            topEnd = 15.dp,
            bottomEnd = 0.dp,
            bottomStart = 15.dp
        )
    ) {
        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                note.title,
                modifier = Modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(20.dp))

            Text(
                note.body,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis


            )


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton({
                    deleteNote()
                }) {
                    Icon(
                        Icons.Default.Delete,
                        null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(Modifier.width(10.dp))

                IconButton(
                    {
                        actionViewModel()
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        null,
                        tint = MaterialTheme.colorScheme.secondary,
                    )
                }

            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    note.date,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Left,
                    fontSize = 10.sp,
                    style = TextStyle(
                        textDirection = TextDirection.Ltr
                    )
                )
            }
        }
    }
}





