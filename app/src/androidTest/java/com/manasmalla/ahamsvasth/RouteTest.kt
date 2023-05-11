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

package com.manasmalla.ahamsvasth

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.manasmalla.ahamsvasth.ui.onboarding.WelcomeScreen
import com.manasmalla.ahamsvasth.ui.theme.AhamSvasthaTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RouteTest {

    @get:Rule
    var composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setupComposable(){
        composeTestRule.setContent {
            AhamSvasthaTheme {
                WelcomeScreen()
            }
        }
    }

    @Test
    fun signInAsGuest_onTap(){
        val guestSignInButton = composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.guest_sign_in_literal))
        guestSignInButton.performClick()
//        composeTestRule.onNodeWithText("About You").assertExists()
    }

    @Test
    fun usernameTextField_emptyField_buttonDisabled(){
        val continueButton = composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.continue_literal))
        continueButton.assertIsNotEnabled()
    }

    @Test
    fun usernameTextField_typeText_continueEnabled(){
        val continueButton = composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.continue_literal))
        val textField = composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.username_literal))
        textField.performTextInput("Manas Malla")
        continueButton.assertIsEnabled()
    }

    @Test
    fun continueButton_onPressed_navigateToRegister(){
        val continueButton = composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.continue_literal))
        continueButton.performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.register_literal)).assertExists()
    }


}