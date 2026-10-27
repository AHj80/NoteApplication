package com.ahj.noteapplication.feature.authentication.presentations.changePass

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.core.common.ui.component.CustomAlertDialog
import com.ahj.noteapplication.core.common.ui.component.CustomAnimateVisibility
import com.ahj.noteapplication.core.common.ui.component.CustomProgress
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.core.common.ui.component.SpacerWidth
import com.ahj.noteapplication.core.common.utils.regexPass
import com.ahj.noteapplication.feature.authentication.component.InsertAuthButton
import com.ahj.noteapplication.feature.authentication.component.InsertTextFieldAuth


@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePassViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent


    Scaffold(
        topBar = { TopBarChangePass { navController.popBackStack() } },
        containerColor = Color.Transparent
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            GlassCard(
                corner = RoundedCornerShape(20.dp),
                modifier = Modifier.imePadding()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            "تغییر رمز عبور",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 25.sp
                        )
                        SpacerWidth(10)

                        ShowInfoPassword()
                    }

                    InsertTextFieldAuth(
                        value = uiState.stateCurrentPass,
                        onValueChange = { onEvent(ChangePassUiEvent.ChangeCurrentPass(it)) },
                        "رمز فعلی"
                    )
                    SpacerHeight(30)

                    InsertTextFieldAuth(
                        value = uiState.stateNewPass,
                        onValueChange = { onEvent(ChangePassUiEvent.ChangeNewPass(it)) },
                        "رمز جدید"
                    )
                    SpacerHeight(10)

                    InsertTextFieldAuth(
                        value = uiState.stateConfirmPass,
                        onValueChange = { onEvent(ChangePassUiEvent.ChangeConfirmPass(it)) },
                        "تکرار رمز جدید"
                    )

                    ConfirmButton(
                        uiState.stateCurrentPass,
                        uiState.stateConfirmPass,
                        uiState.stateNewPass
                    ) { event ->
                        onEvent(event)
                    }
                }
            }


            CustomAnimateVisibility(uiState.status == ChangePassStatus.LOADING) {
                CustomProgress()
            }



            CustomAlertDialog(uiState.message, uiState.stateDialog) {
                onEvent(ChangePassUiEvent.CloseAlertDialog)
            }

        }
    }

}

@Composable
private fun TopBarChangePass(onClick: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        IconButton(
            onClick,
            modifier = Modifier

        ) {
            Icon(
                Icons.AutoMirrored.Default.ArrowForward,
                "Back",
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }

}

@Composable
private fun ShowInfoPassword(
) {

    val visibilityState = remember { MutableTransitionState(false) }
    val showPopup = visibilityState.targetState || visibilityState.currentState


    Box {
        IconButton(
            onClick = {
                if (showPopup && visibilityState.isIdle)
                    visibilityState.targetState = false
                else if (visibilityState.isIdle)
                    visibilityState.targetState = true
            },
            modifier = Modifier.size(24.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "راهنمای رمز عبور",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }


        if (showPopup)
            Popup(
                onDismissRequest = {
                    if (visibilityState.isIdle)
                        visibilityState.targetState = false
                },
                offset = IntOffset(x = 50, 50)
            ) {

                CustomAnimateVisibility(
                    visibilityState
                ) {
                    Card(
                        modifier = Modifier.width(150.dp),
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 0.dp,
                            bottomEnd = 20.dp,
                            bottomStart = 20.dp
                        ),
                        elevation = CardDefaults.cardElevation(5.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "رمز عبور باید بیش از ۸ کاراکتر، بدون فاصله و شامل اعداد و حروف بزرگ و کوچک انگلیسی باشد.",
                            fontSize = 10.sp,
                            modifier = Modifier.padding(12.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }


    }
}

@Composable
private fun ConfirmButton(
    currentPass: String,
    confirmPass: String,
    newPass: String,
    onEvent: (ChangePassUiEvent) -> Unit
) {

    val enabled = remember(currentPass, confirmPass, newPass) {
        currentPass.regexPass() && confirmPass.regexPass() && newPass == confirmPass && newPass.regexPass()
    }


    InsertAuthButton(
        "تغییر رمز عبور",
        enabled = enabled,
        modifier = Modifier.padding(vertical = 10.dp)
    ) { onEvent(ChangePassUiEvent.ConfirmChangePass) }

}
