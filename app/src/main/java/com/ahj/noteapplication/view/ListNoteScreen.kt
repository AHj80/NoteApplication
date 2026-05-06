package com.ahj.noteapplication.view

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahj.noteapplication.data.local.db.entitys.NoteEntity
import com.ahj.noteapplication.navigation.Screens
import com.ahj.noteapplication.viewModel.ListNoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNoteScreen(
    navController: NavController,
    viewModel: ListNoteViewModel = hiltViewModel()
) {

    val note = viewModel.note.collectAsState().value
    val message = viewModel.message
    val stateSearch = viewModel.stateSearching
    val stateSnackBar = viewModel.stateSnackBar.collectAsState().value
    val result = if (stateSearch.isBlank())
        note
    else
        note.filter {
            it.title.contains(
                stateSearch,
                ignoreCase = true
            )
        }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 5.dp, end = 5.dp)
                            .size(width = 270.dp, height = 60.dp),
                        elevation = CardDefaults.cardElevation(10.dp)

                    ) {
                        CreateTextField(
                            stateSearch,
                            stateTextChanged = { viewModel.stateSearching = it },
                            modifier = Modifier
                                .fillMaxSize()
                            /*.padding(top = 8.dp, start = 5.dp, end = 5.dp)*/,
                            leadingIcon = Icons.Default.Search,
                            placeholder = "جست و جو بر اساس عنوان:"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                modifier = Modifier.height(75.dp)
            )


        },
        floatingActionButton = {
            FloatingActionButton(
                { navController.navigate(Screens.CreateNote.route) },
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
            SnackbarHost(stateSnackBar){
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    dismissActionContentColor = MaterialTheme.colorScheme.tertiary
                )
            }
            LaunchedEffect(
                Unit // از خاصیت های کالکت این است که یکبار لانچد افکت اجرا میشود و تا زمان در حال اجرا بودن اسکرین گوش به زنگ مسیج میماند
            ) {
                message.collect {
                    stateSnackBar.showSnackbar(
                        it,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }

            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


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
                                Screens.CreateNote.value(
                                    currentNote.id.toString()
                                )
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

@Composable
fun SampleNoteScreen(
    noteEntity: NoteEntity,
    actionViewModel: () -> Unit,
    deleteNote: () -> Unit,
    modifier: Modifier = Modifier
) {

    val listSize = remember { listOf(250.dp, 190.dp, 200.dp).random() }
    Card(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .width(100.dp)
            .height(listSize)
            .padding(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            /*modifier = modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .width(100.dp)
                .height(200.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(5.dp),*/
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                noteEntity.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Spacer(Modifier.height(20.dp))

            Text(
                if (noteEntity.body.length < 10)
                    noteEntity.body
                else
                    "${noteEntity.body}...",
                color = Color.LightGray,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                textAlign = TextAlign.Center,


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
                    noteEntity.date,
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





