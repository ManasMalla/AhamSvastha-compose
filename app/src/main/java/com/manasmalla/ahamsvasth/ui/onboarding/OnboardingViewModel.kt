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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.manasmalla.ahamsvasth.ui.Destinations
import kotlinx.coroutines.delay

class OnboardingViewModel : ViewModel() {

    var uiState: OnboardingUiState by mutableStateOf(OnboardingUiState.NoUser)
        private set

    suspend fun onHandleOnboardingTransaction(username: String): String {


        uiState = OnboardingUiState.Loading
        delay(1000)
        uiState = OnboardingUiState.NoUser
        //TODO #TRIAGE for login or register
        return if (listOf(
                "manasmalla",
                "sampath",
                "balatripuras"
            ).contains(username)
        ) Destinations.SIGN_IN_ROUTE else Destinations.SIGNUP_ROUTE

    }

    suspend fun onContinueWithGoogle(): String {
        uiState = OnboardingUiState.Loading
        delay(1000)
        uiState = OnboardingUiState.NoUser
        //TODO Not yet implemented
        return Destinations.SURVEY_ROUTE
    }

    fun onForgotPassword() {
        TODO("Not yet implemented")
    }
}