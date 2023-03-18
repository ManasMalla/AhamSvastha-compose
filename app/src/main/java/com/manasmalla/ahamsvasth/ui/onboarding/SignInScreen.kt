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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manasmalla.ahamsvasth.R
import com.manasmalla.ahamsvasth.ui.Destinations
import com.manasmalla.ahamsvasth.ui.theme.AhamSvasthaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    startingUsername: String? = null,
    onNavigateUp: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToSurvey: () -> Unit = {}
) {

    val onboardingViewModel: OnboardingViewModel = viewModel()

    var username by remember {
        mutableStateOf(startingUsername ?: "")
    }
    var password by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(stringResource(R.string.sign_in_literal))
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Rounded.HelpOutline, contentDescription = "Help")
            }
        }, navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        })
    }) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { userInput ->
                    username = userInput
                },
                placeholder = {
                    Text(text = stringResource(R.string.username_literal))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { userInput ->
                    password = userInput
                },
                placeholder = {
                    Text(text = stringResource(R.string.password_literal))
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                trailingIcon = {
                    IconButton(onClick = {
                        showPassword = !showPassword
                    }) {
                        Icon(
                            if (showPassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Button(
                onClick = {
                    focusManager.clearFocus()
                    onNavigateToSurvey()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.sign_in_literal))
            }
            TextButton(onClick = onboardingViewModel::onForgotPassword) {
                Text(text = stringResource(R.string.forgot_password))
            }
            Text(text = stringResource(id = R.string.or_literal))
            OutlinedButton(
                onClick = {
                    when (onboardingViewModel.onContinueWithGoogle()) {
                        Destinations.SURVEY_ROUTE -> {
                            onNavigateToSurvey()
                        }

                        Destinations.DASHBOARD_ROUTE -> {
                            onNavigateToDashboard()
                        }

                        else -> {}
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(stringResource(R.string.continue_with_google))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "te")
@Preview(showBackground = true, locale = "hi")
@Composable
fun SignInScreenPreview() {
    AhamSvasthaTheme {
        SignInScreen()
    }
}