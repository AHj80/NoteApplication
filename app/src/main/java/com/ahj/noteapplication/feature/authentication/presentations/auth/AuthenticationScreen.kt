package com.ahj.noteapplication.feature.authentication.presentations.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ahj.noteapplication.app.navigation.Screens
import com.ahj.noteapplication.core.common.ui.component.CustomAlertDialog
import com.ahj.noteapplication.core.common.ui.component.CustomAnimateVisibility
import com.ahj.noteapplication.core.common.ui.component.CustomProgress
import com.ahj.noteapplication.core.common.ui.component.GlassCard
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.feature.authentication.component.CustomAnimatedContent
import com.ahj.noteapplication.feature.authentication.component.InsertAuthButton
import com.ahj.noteapplication.feature.authentication.component.InsertTextFieldAuth


@Composable
fun AuthenticationScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(uiState.status) {
        if (uiState.status == AuthenticationStatus.SUCCESS) {
            navController.navigate(Screens.VerificationScreen(uiState.stateEmail)) {
                popUpTo<Screens.Authentication> {
                    inclusive = true
                }
            }
        }
    }


    Scaffold(
        bottomBar = {
            BottomBar(
                uiState.changingAuth,
                onEvent,
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomAnimatedContent(
                uiState.changingAuth,
                delay = 0
            ) { auth ->
                if (auth) {
                    Login(
                        uiState.stateEmail,
                        uiState.statePass,
                        uiState.visualTransform,
                        viewModel.validatingLogin(),
                        { onEvent(it) }
                    )
                } else {
                    Register(
                        uiState.stateEmail,
                        uiState.statePass,
                        uiState.statePassConfirm,
                        viewModel.validatingRegister(),
                        uiState.visualTransform,
                        { onEvent(it) }
                    )


                }
            }

            ForgetPassword { navController.navigate(Screens.ForgetPassword) }

        }

        CustomAnimateVisibility(
            uiState.status == AuthenticationStatus.LOADING
        ) {
            CustomProgress()
        }


        CustomAlertDialog(uiState.error, uiState.status == AuthenticationStatus.ERROR) {
            onEvent(AuthenticationUiEvent.OnDismiss)
        }


    }


}


@Composable
private fun Register(
    email: String,
    pass: String,
    passConfirm: String,
    enabled: Boolean,
    visualTransform: Boolean,
    onEvent: (AuthenticationUiEvent) -> Unit
) {


    GlassCard {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "خوش آمدید",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 35.sp,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Normal
            )
            SpacerHeight(20)

            InsertTextFieldAuth(
                email,
                {
                    onEvent(AuthenticationUiEvent.ChangeEmail(it))
                },
                "ایمیل خود را وارد نمایید",
                keyboardType = KeyboardType.Email
            )
            SpacerHeight(20)

            InsertTextFieldAuth(
                pass,
                {
                    onEvent(AuthenticationUiEvent.ChangePass(it))
                },
                "رمز خود را وارد نمایید",
                keyboardType = KeyboardType.Password,
                visualTransformation = visualTransform,
                leadingIcon = {
                    IconButton({ onEvent(AuthenticationUiEvent.Transform) }) {
                        if (visualTransform) {
                            Icon(
                                Icons.Default.Visibility,
                                contentDescription = null,
                            )
                        } else {
                            Icon(
                                Icons.Default.VisibilityOff,
                                contentDescription = null,
                            )
                        }
                    }
                }
            )
            SpacerHeight(20)

            InsertTextFieldAuth(
                passConfirm,
                {
                    onEvent(AuthenticationUiEvent.ChangePassConfirm(it))
                },
                "رمز خود تکرار نمایید",
                keyboardType = KeyboardType.Password,
                visualTransformation = visualTransform
            )
            SpacerHeight(30)

            InsertAuthButton("ثبت نام", enabled = enabled) {
                onEvent(AuthenticationUiEvent.Register)
            }
            SpacerHeight(20)
        }
    }


}


@Composable
private fun Login(
    email: String,
    pass: String,
    transform: Boolean,
    enabled: Boolean,
    onEvent: (AuthenticationUiEvent) -> Unit
) {

    GlassCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "خوش آمدید",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 35.sp,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Normal
            )
            SpacerHeight(20)

            InsertTextFieldAuth(
                email,
                {
                    onEvent(AuthenticationUiEvent.ChangeEmail(it))
                },
                "ایمیل خود را وارد نمایید",
                keyboardType = KeyboardType.Email
            )
            SpacerHeight(20)

            InsertTextFieldAuth(
                pass,
                {
                    onEvent(AuthenticationUiEvent.ChangePass(it))
                },
                "رمز خود را وارد نمایید",
                keyboardType = KeyboardType.Password,
                visualTransformation = transform,
                leadingIcon = {
                    IconButton({ onEvent(AuthenticationUiEvent.Transform) }) {
                        if (transform) {
                            Icon(
                                Icons.Default.Visibility,
                                contentDescription = null,
                            )
                        } else {
                            Icon(
                                Icons.Default.VisibilityOff,
                                contentDescription = null,
                            )
                        }
                    }
                }
            )
            SpacerHeight(20)

            InsertAuthButton("ورود", enabled = enabled) {
                onEvent(AuthenticationUiEvent.Login)
            }
            SpacerHeight(20)
        }
    }

}

@Composable
private fun BottomBar(
    changeAuth: Boolean,
    onEvent: (AuthenticationUiEvent) -> Unit
) {

    val loginColor by animateColorAsState(
        targetValue = if (changeAuth) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(500)
    )
    val registerColor by animateColorAsState(
        targetValue = if (!changeAuth) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = tween(500)
    )

    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .background(Color.Gray, shape = CircleShape)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(
                color = loginColor,
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onEvent(AuthenticationUiEvent.ChangeAuthentication(true))
                    },
                shape = CircleShape
            ) {
                Text(
                    "ورود",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }

            Surface(
                color = registerColor,
                modifier = Modifier
                    .width(110.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onEvent(AuthenticationUiEvent.ChangeAuthentication(false))
                    },
                shape = CircleShape
            ) {
                Text(
                    "ثبت نام",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}


@Composable
fun ForgetPassword(onEvent: (AuthenticationUiEvent) -> Unit) {
    TextButton({
        onEvent(AuthenticationUiEvent.ForgetPass)
    }) {
        Text(
            "فراموشی رمز عبور",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
