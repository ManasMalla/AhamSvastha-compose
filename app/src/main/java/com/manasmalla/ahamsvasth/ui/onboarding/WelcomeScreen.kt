/*
 * Copyright © 2023 Manas Malla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.manasmalla.ahamsvasth.ui.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manasmalla.ahamsvasth.R
import com.manasmalla.ahamsvasth.ui.Destinations.DASHBOARD_ROUTE
import com.manasmalla.ahamsvasth.ui.Destinations.SIGNUP_ROUTE
import com.manasmalla.ahamsvasth.ui.Destinations.SIGN_IN_ROUTE
import com.manasmalla.ahamsvasth.ui.Destinations.SURVEY_ROUTE
import com.manasmalla.ahamsvasth.ui.theme.AhamSvasthaTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreen(
    onNavigateToSignIn: (String) -> Unit = {},
    onNavigateToSignUp: (String) -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToSurvey: () -> Unit = {}
) {

    val onboardingViewModel: OnboardingViewModel = viewModel()

    AnimatedContent(targetState = onboardingViewModel.uiState) { uiStateValue ->
        when (uiStateValue) {
            OnboardingUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(
                            Alignment.Center
                        )
                )
            }

            OnboardingUiState.NoUser -> {
                Column {
                    AhamSvasthaTitle(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .wrapContentSize(Alignment.Center)
                    )
                    SignInSection(
                        onHandleOnboardingTransaction = { username ->
                            when (onboardingViewModel.onHandleOnboardingTransaction(username)) {
                                SIGNUP_ROUTE -> {
                                    onNavigateToSignUp(username)
                                }

                                SIGN_IN_ROUTE -> {
                                    onNavigateToSignIn(username)
                                }

                                else -> {}
                            }
                        },
                        onNavigateToSurvey = onNavigateToSurvey,
                        onContinueWithGoogle = {
                            when (onboardingViewModel.onContinueWithGoogle()) {
                                SURVEY_ROUTE -> {
                                    onNavigateToSurvey()
                                }

                                DASHBOARD_ROUTE -> {
                                    onNavigateToDashboard()
                                }

                                else -> {}
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AhamSvasthaTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Rounded.SelfImprovement,
            contentDescription = null,
            modifier = Modifier.size(128.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(id = R.string.app_headline),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSection(
    modifier: Modifier = Modifier,
    onHandleOnboardingTransaction: (String) -> Unit = {},
    onContinueWithGoogle: () -> Unit = {},
    onNavigateToSurvey: () -> Unit = {}
) {
    var username by remember {
        mutableStateOf("")
    }
    val canUserContinue by remember {
        derivedStateOf {
            username.isNotBlank()
        }
    }
    val focusManager = LocalFocusManager.current
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(24.dp)) {
        Text(
            text = stringResource(R.string.sign_in_description_literal),
            modifier = Modifier.padding(12.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { userInput ->
                username = userInput
            },
            placeholder = {
                Text(text = stringResource(id = R.string.username_literal))
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                if (canUserContinue) {
                    onHandleOnboardingTransaction(username)
                }
            })
        )
        Button(
            onClick = {
                onHandleOnboardingTransaction(username)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), enabled = canUserContinue
        ) {
            Text(stringResource(R.string.continue_literal))
        }
        Text(text = stringResource(R.string.or_literal), modifier = Modifier.padding(8.dp))
        OutlinedButton(
            onClick = onNavigateToSurvey, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.guest_sign_in_literal))
        }
        OutlinedButton(
            onClick = onContinueWithGoogle, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(stringResource(R.string.continue_with_google))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInSectionPreview() {
    AhamSvasthaTheme {
        SignInSection()
    }
}

@Preview(showBackground = true, locale = "hi", name = "Hindi")
@Preview(showBackground = true, locale = "te", name = "Telugu")
@Preview(showBackground = true, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Preview(showBackground = true, wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE)
@Preview(showBackground = true, wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
@Preview(showBackground = true, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun WelcomeScreenPreview() {
    AhamSvasthaTheme {
        WelcomeScreen()
    }
}