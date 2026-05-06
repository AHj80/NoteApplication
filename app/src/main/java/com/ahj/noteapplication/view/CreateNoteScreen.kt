package com.ahj.noteapplication.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahj.noteapplication.viewModel.CreateNoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: CreateNoteViewModel = hiltViewModel(),
    id: Int,
) {



    val message = viewModel.message
    val enable =
        viewModel.stateTitle.isNotBlank() && viewModel.stateBody.isNotBlank() && viewModel.isChanged
    val stateFontSize = viewModel.stateFontSize.collectAsState()
    val stateSnackBar = viewModel.stateSnackBar.collectAsState().value
    /*val keyboardController = LocalSoftwareKeyboardController.current*/ // با استفاده از این متد میتوانیم کیبورد را بعد از تایید کاربر ببندیم به شکل زیر

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton({
                            viewModel.stateDropDown = true
                        }) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                null
                            )
                        }
                        Text("سایز فونت : ${stateFontSize.value}")
                    }
                },
                actions = {
                    if (viewModel.isChanged)
                        IconButton(
                            {
                                viewModel.saveNote()
                                /*keyboardController?.hide()*/ // به این شکل هاید میکنیم کیبورد را
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

                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 7.dp, bottom = 7.dp),
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
            )

        },
        snackbarHost = {
            SnackbarHost(stateSnackBar,
                modifier = Modifier
                    .imePadding() // این قابلیت برای این است که کاری کند تا اسنک بار زمان ایجاد اندازه کیبورد را محاسبه کند و اسنک بار را بر اساس ارتفاع ان در بالای کیبورد قرار دهد
                    // نکته مهم اینکه برای کارکردن نیاز دارد در اندروید مانیفست قسمت اکتیویتی دستور زیر وجود داشته باشد زیرا میگوید که از اینکه کیبورد روی همه چیز قرار بگیرد جلوگیری کن و ریسپانسیو کن
                    // android:windowSoftInputMode="adjustResize"
                    .padding(bottom = 16.dp)
            ){
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.background,
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
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DropdownMenu(
                expanded = viewModel.stateDropDown,
                onDismissRequest = { viewModel.stateDropDown = false },
                offset = DpOffset(x = 135.dp, y = 100.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                viewModel.listSize.forEach { size ->

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
                                if (stateFontSize.value == size) {
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
                stateText = viewModel.stateTitle,
                stateTextChanged = { viewModel.onChangedTitle(it) },
                placeholder = "موضوع:تعطیلات تابستانی",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp),
                material = MaterialTheme.typography.titleLarge
            )


            Spacer(Modifier.height(10.dp))

            CreateTextField(
                stateText = viewModel.stateBody,
                stateTextChanged = { viewModel.onChangedBody(it) },
                Modifier
                    .fillMaxSize()
                    .padding(7.dp),
                placeholder = "متن : ...",
                singleLine = false,
                fontSize = stateFontSize.value,
                material = MaterialTheme.typography.bodyLarge
            )


        }

    }

}

@Composable
fun CreateTextField(
    stateText: String,
    stateTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    singleLine: Boolean = true,
    leadingIcon: ImageVector? = null,
    fontSize: Int = 16,
    material: TextStyle = MaterialTheme.typography.bodyLarge
) {

    TextField(
        value = stateText,
        onValueChange = stateTextChanged,
        modifier = modifier.size(width = 270.dp, height = 60.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            errorContainerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.tertiary
        ),
        shape = RoundedCornerShape(5.dp),
        textStyle = material.copy(
            fontSize = fontSize.sp
        ),
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )
        },
        singleLine = singleLine,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.scale(1.5f)
                )
            }
        }

    )
}