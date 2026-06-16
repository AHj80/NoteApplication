package com.ahj.noteapplication.feature.note.presentation.note_creation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahj.noteapplication.core.common.ui.component.CreateTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: CreateNoteViewModel = hiltViewModel(),
    id: Int,
) {


    val state by viewModel.uiState.collectAsState()
    val enable =
        state.stateTitle.isNotBlank() && state.stateBody.isNotBlank() && state.isChanged


    LaunchedEffect(
        state.message
    ) {
        state.message?.let { msg ->
            state.snackBar.showSnackbar(
                msg,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton({
                            viewModel.setDropDownMenu(true)
                        }) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                null
                            )
                        }
                        Text("سایز فونت : ${state.stateFont}")
                    }
                },
                actions = {
                    if (state.isChanged)
                        IconButton(
                            {
                                viewModel.saveOrEditNote()
                            },
                            enabled = enable

                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = if (enable)
                                    MaterialTheme.colorScheme.secondary
                                else
                                    Color.Gray,

                                modifier = Modifier.scale(1.5f)
                            )
                        }

                },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .scale(1.5f)
                            .clickable {
                                navController.popBackStack()
                            }
                            .padding(start = 10.dp)
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
            )

        },
        snackbarHost = {
            SnackbarHost(
                state.snackBar,
                modifier = Modifier
                    .imePadding()
                    .padding(bottom = 16.dp)
            ) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    dismissActionContentColor = MaterialTheme.colorScheme.tertiary
                )
            }

        }
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            DropdownMenu(
                expanded = state.stateDropDown,
                onDismissRequest = { viewModel.setDropDownMenu(false) },
                offset = DpOffset(x = 135.dp, y = 100.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                state.fontList.forEach { size ->

                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "$size",
                                    fontSize = size.sp,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                                if (state.stateFont == size) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                    )
                                }
                            }
                        },
                        onClick = {
                            viewModel.selectFontSize(size)
                        }
                    )
                }
            }

            LaunchedEffect(Unit) {
                if (id > 0)
                    viewModel.getNoteById(id)
            }

            CreateTextField(
                stateText = state.stateTitle,
                stateTextChanged = { viewModel.updateTextTitle(it) },
                placeholder = "موضوع:تعطیلات تابستانی",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp),
                material = MaterialTheme.typography.titleLarge
            )


            Spacer(Modifier.height(10.dp))

            CreateTextField(
                stateText = state.stateBody,
                stateTextChanged = { viewModel.updateTextBody(it) },
                Modifier
                    .fillMaxSize()
                    .padding(7.dp),
                placeholder = "متن : ...",
                singleLine = false,
                fontSize = state.stateFont,
                material = MaterialTheme.typography.bodyLarge
            )


        }

    }

}

