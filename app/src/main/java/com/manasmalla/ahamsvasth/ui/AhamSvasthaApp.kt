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

package com.manasmalla.ahamsvasth.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.manasmalla.ahamsvasth.ui.onboarding.SignInScreen
import com.manasmalla.ahamsvasth.ui.onboarding.SignUpScreen
import com.manasmalla.ahamsvasth.ui.onboarding.SurveyScreen
import com.manasmalla.ahamsvasth.ui.onboarding.WelcomeScreen

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SIGN_IN_ROUTE = "sign_in/{username}"
    const val SIGNUP_ROUTE = "signup/{username}"
    const val DASHBOARD_ROUTE = "dashboard"
    const val SURVEY_ROUTE = "survey"
}

@Composable
fun AhamSvasthaApp(isFirstRun: Boolean) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (isFirstRun) (if(Firebase.auth.currentUser == null) Destinations.WELCOME_ROUTE else Destinations.SURVEY_ROUTE) else Destinations.DASHBOARD_ROUTE,
    ) {
        composable(Destinations.WELCOME_ROUTE) {
            WelcomeScreen(onNavigateToSignIn = { username ->
                navController.navigate("sign_in/$username")
            }, onNavigateToSignUp = { username ->
                navController.navigate("signup/$username")
            }, onNavigateToSurvey = {
                navController.navigate(Destinations.SURVEY_ROUTE)
            }, onNavigateToDashboard = {
                navController.navigate(Destinations.DASHBOARD_ROUTE)
            })
        }
        composable(Destinations.SIGNUP_ROUTE) {
            val username = it.arguments?.getString("username")
            SignUpScreen(startingUsername = username, onNavigateUp = {
                navController.navigateUp()
            }, onNavigateToSurvey = {
                navController.navigate(Destinations.SURVEY_ROUTE)
            })
        }
        composable(Destinations.SIGN_IN_ROUTE) {
            val username = it.arguments?.getString("username")
            SignInScreen(startingUsername = username, onNavigateUp = {
                navController.navigateUp()
            }, onNavigateToSurvey = {
                navController.navigate(Destinations.SURVEY_ROUTE)
            }, onNavigateToDashboard = {
                navController.navigate(Destinations.DASHBOARD_ROUTE)
            })
        }
        composable(Destinations.SURVEY_ROUTE) {
            SurveyScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                onRegister = {
                    navController.navigate(Destinations.DASHBOARD_ROUTE)
                }
            )
        }
        composable(Destinations.DASHBOARD_ROUTE) {
            Text("Dashboard")
        }
    }
}