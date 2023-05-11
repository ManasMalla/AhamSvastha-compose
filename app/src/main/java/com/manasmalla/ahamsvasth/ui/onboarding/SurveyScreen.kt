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

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.manasmalla.ahamsvasth.ui.components.SegmentedControl
import com.manasmalla.ahamsvasth.ui.theme.AhamSvasthaTheme
import kotlinx.coroutines.launch

private tailrec fun Context.findActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SurveyScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    onRegister: () -> Unit = {}
) {
    val onboardingViewModel: OnboardingViewModel = viewModel()
    val context = LocalContext.current

    val canUserContinue by remember {
        derivedStateOf {
            onboardingViewModel.surveyState.age.isNotBlank() && onboardingViewModel.surveyState.height.isNotBlank() && onboardingViewModel.surveyState.weight.isNotBlank()
        }
    }
    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { }, actions = {
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
    }, snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) {
        when(onboardingViewModel.uiState){
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
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    val diseases = listOf("Diabetes", "Thyroid", "Cholestrol", "Blood Pressure", "Obesity")
                    Icon(
                        Icons.Rounded.VolunteerActivism,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "About You",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = "The information you provide will help us know you better and tailor the app experience just for you")
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Gender", style = MaterialTheme.typography.labelLarge)
                            SegmentedControl(
                                items = listOf("Male", "Female"),
                                selectedItemIndex = onboardingViewModel.surveyState.gender,
                                onItemSelection = onboardingViewModel::updateGender
                            )
                        }
                        OutlinedTextField(
                            value = onboardingViewModel.surveyState.age,
                            onValueChange = onboardingViewModel::updateAge,
                            placeholder = {
                                Text(text = "Age")
                            },
                            shape = MaterialTheme.shapes.medium
                        )

                    }
                    AnimatedVisibility(onboardingViewModel.shouldAskPeriodDate) {
                        OutlinedButton(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth(),
                            onClick = {
                                val fragmentManager =
                                    context.findActivity().supportFragmentManager
                                onboardingViewModel.showDatePicker(fragmentManager)
                            }
                        ) {
                            Text(
                                text = onboardingViewModel.formattedPeriodDate,
                                modifier = if (onboardingViewModel.surveyState.period != null) Modifier.padding(
                                    vertical = 12.dp
                                ) else Modifier
                            )
                            Icon(
                                Icons.Rounded.ArrowDropDown,
                                contentDescription = "Open Date Picker",
                                modifier = Modifier
                                    .weight(1f)
                                    .wrapContentWidth(Alignment.End)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {

                        OutlinedTextField(
                            value = onboardingViewModel.surveyState.height,
                            onValueChange = onboardingViewModel::updateHeight,
                            placeholder = {
                                Text(text = "Height")
                            },
                            trailingIcon = {
                                Text(text = "cm", style = MaterialTheme.typography.labelLarge)
                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = onboardingViewModel.surveyState.weight,
                            onValueChange = onboardingViewModel::updateWeight,
                            placeholder = {
                                Text(text = "Weight")
                            },
                            trailingIcon = {
                                Text(text = "kg", style = MaterialTheme.typography.labelLarge)
                            },
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f)
                        )

                    }

                    Divider(modifier = Modifier.padding(vertical = 24.dp))
                    Text(
                        text = "Lifestyle",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "The information you provide will help us know you better and tailor the app experience just for you",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                    SegmentedControl(
                        items = listOf("Sedentary", "Active", "Hectic"),
                        selectedItemIndex = onboardingViewModel.surveyState.lifestyle,
                        onItemSelection = onboardingViewModel::updateLifestyle,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        diseases.forEach { disease ->
                            InputChip(
                                selected = onboardingViewModel.surveyState.diseases.contains(disease),
                                onClick = {
                                    if (onboardingViewModel.surveyState.diseases.contains(disease)) onboardingViewModel.surveyState.diseases.remove(
                                        disease
                                    ) else onboardingViewModel.surveyState.diseases.add(disease)
                                },
                                label = {
                                    Text(text = disease)
                                })
                        }
                    }

                    Button(
                        onClick = {
                            onboardingViewModel.submitSurvey(context, showSnackbar = {message ->
                                scope.launch{
                                    snackbarHostState.showSnackbar(message)
                                }
                            }) {
                                onRegister()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp), enabled = canUserContinue
                    ) {
                        Text(text = "Get Started")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyScreenPreview() {
    AhamSvasthaTheme {
        SurveyScreen()
    }
}