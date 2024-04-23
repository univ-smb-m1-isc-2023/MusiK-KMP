package com.github.enteraname74.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.github.enteraname74.Constants
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.event.UserScreenEvent
import com.github.enteraname74.model.settings.ViewSettingsHandler
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.viewmodel.UserScreenModel

/**
 * View of the user screen.
 */
class UserScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<UserScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val state by screenModel.state.collectAsState()
        val viewSettingsHandler = injectElement<ViewSettingsHandler>()

        if (viewSettingsHandler.user != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MusikColorTheme.colorScheme.primary
                    )
                    .padding(Constants.Spacing.medium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = MusikColorTheme.colorScheme.onPrimary
                )
            }
            screenModel.onEvent(
                UserScreenEvent.AuthenticateUser
            )
            navigator.push(
                HomeScreen()
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MusikColorTheme.colorScheme.primary
                    )
                    .padding(Constants.Spacing.medium),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = appStrings.appName,
                    color = MusikColorTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(Constants.Spacing.large),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = state.username,
                        onValueChange = {
                            screenModel.onEvent(
                                UserScreenEvent.SetUsername(it)
                            )
                        },
                        label = {
                            Text(
                                text = appStrings.username
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = MusikColorTheme.colorScheme.onPrimary,
                            focusedTextColor = MusikColorTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MusikColorTheme.colorScheme.onPrimary,
                            focusedLabelColor = MusikColorTheme.colorScheme.onPrimary,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = MusikColorTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = MusikColorTheme.colorScheme.secondary,
                            focusedIndicatorColor = MusikColorTheme.colorScheme.secondary,
                        )
                    )
                    TextField(
                        value = state.password,
                        onValueChange = {
                            screenModel.onEvent(
                                UserScreenEvent.SetPassword(it)
                            )
                        },
                        label = {
                            Text(
                                text = appStrings.password
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = MusikColorTheme.colorScheme.onPrimary,
                            focusedTextColor = MusikColorTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MusikColorTheme.colorScheme.onPrimary,
                            focusedLabelColor = MusikColorTheme.colorScheme.onPrimary,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = MusikColorTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = MusikColorTheme.colorScheme.secondary,
                            focusedIndicatorColor = MusikColorTheme.colorScheme.secondary,
                        )
                    )
                    Button(
                        onClick = {
                            screenModel.onEvent(
                                UserScreenEvent.ConnectUser
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MusikColorTheme.colorScheme.secondary
                        ),
                        enabled = state.username.isNotBlank() && state.password.isNotBlank()
                    ) {
                        Text(
                            text = appStrings.connect,
                            color = MusikColorTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}