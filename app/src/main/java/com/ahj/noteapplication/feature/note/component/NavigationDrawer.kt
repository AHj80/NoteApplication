package com.ahj.noteapplication.feature.note.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.ahj.noteapplication.core.common.ui.component.SpacerHeight
import com.ahj.noteapplication.core.common.utils.biometricHelper
import com.ahj.noteapplication.core.common.utils.openUrl
import com.ahj.noteapplication.feature.note.domain.model.DrawerItems
import com.ahj.noteapplication.feature.note.domain.model.DrawerItemsModel
import com.ahj.noteapplication.feature.note.presentation.noteList.ListNoteUiEvent


@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    biometric: Boolean,
    email: String,
    onEvent: (ListNoteUiEvent) -> Unit,
    content: @Composable () -> Unit
) {


    val listItems = listOf(
        DrawerItemsModel(
            DrawerItems.CHANGE_PASS,
            "تغییر رمزعبور"
        ),
        DrawerItemsModel(
            DrawerItems.CONTACTS,
            "ارتباط با توسعه دهنده"
        ),
        DrawerItemsModel(
            DrawerItems.LOGOUT,
            "خروج"
        ),
    )
    val context = LocalContext.current
    val activity = context as? FragmentActivity

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth(0.7f),
                drawerTonalElevation = 20.dp,
                windowInsets = WindowInsets(top = 0)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxSize()
                ) {
                    SpacerHeight(40)
                    Text(
                        "داشبورد کاربری",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 30.sp
                    )
                    SpacerHeight(20)

                    Card(
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                        elevation = CardDefaults.cardElevation(7.dp),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                email,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 10.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    SpacerHeight(40)

                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text(
                                "ورود با اثرانگشت",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Switch(
                                biometric,
                                { checkChange ->

                                    activity?.let { activity ->
                                        biometricHelper(
                                            activity,
                                            "ورود با اثر انگشت",
                                            "",
                                            {
                                                onEvent(ListNoteUiEvent.BiometricChange(checkChange))
                                            },
                                            {
                                                Toast.makeText(
                                                    context,
                                                    "اثرانگشت احراز نشد!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = Color.DarkGray,
                                    uncheckedBorderColor = Color.Gray,
                                    uncheckedTrackColor = Color.LightGray,
                                ),
                                modifier = Modifier.scale(0.6f)

                            )
                        }
                    }

                    listItems.forEach { item ->

                        TextButton(
                            {
                                when (item.id) {

                                    DrawerItems.CHANGE_PASS -> {
                                        onEvent(ListNoteUiEvent.ChangePassNavigate)
                                    }

                                    DrawerItems.CONTACTS -> {
                                        context.openUrl(
                                            "https://t.me/AHj80",
                                            "org.telegram.messenger"
                                        )
                                    }

                                    DrawerItems.LOGOUT -> {
                                        onEvent(ListNoteUiEvent.Logout)
                                    }
                                }
                            },
                            modifier = Modifier.padding(
                                top = if (item.id == DrawerItems.LOGOUT) 40.dp else 0.dp
                            )
                        ) {

                            Text(
                                item.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (item.id == DrawerItems.LOGOUT)
                                    Color.Red
                                else
                                    MaterialTheme.colorScheme.primary
                            )

                        }


                    }
                }
            }
        },
        content = content
    )

}