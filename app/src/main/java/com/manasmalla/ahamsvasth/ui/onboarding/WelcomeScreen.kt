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

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manasmalla.ahamsvasth.R
import com.manasmalla.ahamsvasth.services.GoogleAuthService
import com.manasmalla.ahamsvasth.ui.AhamSvasthaIcon
import com.manasmalla.ahamsvasth.ui.Destinations.SIGNUP_ROUTE
import com.manasmalla.ahamsvasth.ui.Destinations.SIGN_IN_ROUTE
import com.manasmalla.ahamsvasth.ui.theme.AhamSvasthaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignIn: (String) -> Unit = {},
    onNavigateToSignUp: (String) -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToSurvey: () -> Unit = {}
) {

    val onboardingViewModel: OnboardingViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val signInIntentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            onboardingViewModel.onSignInActivityResult(true, scope, result, context, snackbarHostState, onNavigateToSurvey, onNavigateToDashboard)
        })
    val signUpIntentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            onboardingViewModel.onSignInActivityResult(false, scope, result, context, snackbarHostState, onNavigateToSurvey, onNavigateToDashboard)
        })

    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) { scaffoldPadding ->
        AnimatedContent(
            targetState = onboardingViewModel.uiState, modifier = modifier.padding(scaffoldPadding),
            label = ""
        ) { uiStateValue ->
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

                else -> {
                    Column {
                        AhamSvasthaTitle(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .wrapContentSize(Alignment.Center)
                        )
                        SignInSection(
                            onHandleOnboardingTransaction = { username ->
                                scope.launch {
                                    when (onboardingViewModel.onHandleOnboardingTransaction(username)) {
                                        SIGNUP_ROUTE -> {
                                            onNavigateToSignUp(username)
                                            onboardingViewModel.onNavigate(OnboardingUiState.SignUp)
                                        }

                                        SIGN_IN_ROUTE -> {
                                            onNavigateToSignIn(username)
                                            onboardingViewModel.onNavigate(OnboardingUiState.SignIn)
                                        }

                                        else -> {}
                                    }
                                }
                            },
                            onNavigateToSurvey = onNavigateToSurvey,
                            onContinueWithGoogle = {

                                GoogleAuthService().loginUser(context, signInIntentLauncher, signUpIntentLauncher) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(message = it)
                                    }
                                }

                            },
                            onContinueAsGuest = { onSuccess ->
                                onboardingViewModel.continueAsGuest(onSuccess){
                                    scope.launch {
                                        snackbarHostState.showSnackbar(it)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AhamSvasthaTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AhamSvasthaIcon()
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = stringResource(id = R.string.app_headline),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            modifier = Modifier.padding(4.dp),
            fontWeight = FontWeight.Normal
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSection(
    modifier: Modifier = Modifier,
    onHandleOnboardingTransaction: (String) -> Unit = {},
    onContinueWithGoogle: () -> Unit = {},
    onNavigateToSurvey: () -> Unit = {},
    onContinueAsGuest: (()->Unit)->Unit = {}
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
            onClick = {
                onContinueAsGuest{

                    onNavigateToSurvey
                }
                      }, modifier = Modifier
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
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    AhamSvasthaTheme {
        WelcomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreSPreview() {
    AhamSvasthaTheme(dynamicColor = false) {
        WelcomeScreen()
    }
}