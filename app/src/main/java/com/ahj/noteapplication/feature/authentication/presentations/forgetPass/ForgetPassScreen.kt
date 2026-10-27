package com.ahj.noteapplication.feature.authentication.presentations.forgetPass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.ui.component.CustomAlertDialog
import com.ahj.noteapplication.core.common.ui.component.CustomProgress
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.core.common.utils.regexEmail
import com.ahj.noteapplication.feature.authentication.component.InsertAuthButton
import com.ahj.noteapplication.feature.authentication.component.InsertTextFieldAuth


@Composable
fun ForgetPassScreen(
    navController: NavController,
    viewModel: ForgetPassViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.status) {
        if (uiState.status == ForgetPassStatus.VERIFY_SUCCESS) {
            navController.navigate(Screens.ListNoteScreens) {
                popUpTo<Screens.ForgetPassword> { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "ابتدا ایمیل خود را وارد نمایید و پس از آن از طریق کد تایید ارسال شده به آدرس ایمیل ، وارد حساب کاربری خود شوید.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(20.dp)
        )
        SpacerHeight(10)

        EmailVerify(uiState.stateEmail, uiState.enableOtp) {
            viewModel.onEvent(
                it
            )
        }
        SpacerHeight(20)

        VerifyOtp(
            uiState.stateOtp,
            uiState.enableOtp,
            uiState.timer,
            uiState.otpValid
        ) { viewModel.onEvent(it) }
    }

    if (uiState.status == ForgetPassStatus.LOADING) CustomProgress()

    CustomAlertDialog(
        uiState.message,
        uiState.dialog
    ) { viewModel.onEvent(ForgetPassUiEvent.OnDismiss) }
}

@Composable
private fun EmailVerify(
    email: String,
    enable: Boolean,
    onEvent: (ForgetPassUiEvent) -> Unit
) {

    GlassCard(
        corner = RoundedCornerShape(20.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            InsertTextFieldAuth(
                value = email,
                onValueChange = { onEvent(ForgetPassUiEvent.ChangeStateEmail(it)) },
                placeholder = "ایمیل خود را وارد نمایید"
            )
            SpacerHeight(10)

            InsertAuthButton(
                "ارسال کد تایید",
                !enable && email.regexEmail()
            ) {
                onEvent(ForgetPassUiEvent.SendOtp)
            }
        }
    }

}

@Composable
private fun VerifyOtp(
    otp: String,
    enable: Boolean,
    timer: Int,
    valid: Boolean,
    onEvent: (ForgetPassUiEvent) -> Unit
) {

    GlassCard(
        corner = RoundedCornerShape(20.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            InsertTextFieldAuth(
                value = otp,
                onValueChange = { onEvent(ForgetPassUiEvent.ChangeStateOtp(it)) },
                placeholder = "کد تایید را وارد نمایید"
            )
            SpacerHeight(10)


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                InsertAuthButton(
                    "تایید",
                    enable && valid
                ) {
                    onEvent(ForgetPassUiEvent.VerifyOtp)
                }

                Box(modifier = Modifier.width(70.dp), contentAlignment = Alignment.Center) {
                    Text(
                        "0:$timer",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(10.dp),
                        color = Color.White
                    )
                }


            }
        }
    }

}