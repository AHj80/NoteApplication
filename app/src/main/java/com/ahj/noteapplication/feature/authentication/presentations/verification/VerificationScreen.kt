package com.ahj.noteapplication.feature.authentication.presentations.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.ui.component.CustomAlertDialog
import com.ahj.noteapplication.core.common.ui.component.CustomAnimateVisibility
import com.ahj.noteapplication.core.common.ui.component.CustomProgress
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.core.common.ui.component.SpacerWidth
import com.ahj.noteapplication.feature.authentication.component.InsertAuthButton
import com.ahj.noteapplication.feature.authentication.component.InsertTextFieldAuth


@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: VerificationViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(uiState.status) {
        if (uiState.status == VerificationStatus.SUCCESS) {
            navController.navigate(Screens.ListNoteScreens) {
                popUpTo<Screens.VerificationScreen> {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ScreenItems(
            uiState.stateOtp,
            uiState.timer,
        ) { onEvent(it) }


    }
    CustomAnimateVisibility(
        uiState.status == VerificationStatus.LOADING
    ) {
        CustomProgress()
    }

    CustomAlertDialog(
        uiState.message, uiState.alert
    ) {
        onEvent(VerificationUiEvent.OnDismiss)
    }


}

@Composable
private fun ScreenItems(
    otp: String,
    time: Int,
    onEvent:(VerificationUiEvent)-> Unit
) {

    val validateEnabled = otp.isNotBlank() && otp.length == 8

    InsertTextFieldAuth(
        otp,
        { onEvent(VerificationUiEvent.ChangeOTPState(it)) },
        "کد تایید را وارد نمایید",
        keyboardType = KeyboardType.Number
    )
    SpacerHeight(10)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        InsertAuthButton("تایید", enabled = validateEnabled) {
            onEvent(VerificationUiEvent.ConfirmClick)
        }

        TextButton({
            onEvent(VerificationUiEvent.ResendOtp)
        }, enabled = time == 0) {
            Text(
                "ارسال مجدد کد",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
            )
        }
        SpacerWidth(5)

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),

            ) {

                Text(
                    "00:$time",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                    color = MaterialTheme.colorScheme.secondary
                )

        }
    }

}