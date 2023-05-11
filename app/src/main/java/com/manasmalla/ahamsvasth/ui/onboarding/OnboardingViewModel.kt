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
import androidx.activity.result.ActivityResult
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.manasmalla.ahamsvasth.IS_FIRST_RUN_KEY
import com.manasmalla.ahamsvasth.dataStore
import com.manasmalla.ahamsvasth.services.GoogleAuthService
import com.manasmalla.ahamsvasth.ui.Destinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OnboardingViewModel : ViewModel() {

    var uiState: OnboardingUiState by mutableStateOf(OnboardingUiState.NoUser)
        private set

    var surveyState: UserSurveyData by mutableStateOf(UserSurveyData())
        private set

    val shouldAskPeriodDate by derivedStateOf {
        surveyState.gender == 1 && (surveyState.age.toIntOrNull() ?: 0) > 10
    }

    val formattedPeriodDate: String by derivedStateOf {
        if (surveyState.period == null) "When did your last period start?" else SimpleDateFormat(
            "EEE, MMM dd",
            Locale.getDefault()
        ).format(
            Date(
                surveyState.period!!
            )
        )
    }

    private suspend fun getFirestoreUsers() = callbackFlow<QuerySnapshot> {
        val db = Firebase.firestore
        db.collection("users").get().addOnSuccessListener { result ->
            trySend(result)
        }
        awaitClose { }
    }

    suspend fun onHandleOnboardingTransaction(username: String): String {

        uiState = OnboardingUiState.Loading
        delay(1000)
        val destination = getFirestoreUsers().map { result ->
            if (result.any {
                    it["username"].toString().lowercase() == username.lowercase()
                }) Destinations.SIGN_IN_ROUTE else Destinations.SIGNUP_ROUTE
        }
        return destination.first()
    }

    private fun onContinueWithGoogle(isSignIn: Boolean): String {
        uiState = OnboardingUiState.Loading
        uiState = OnboardingUiState.NoUser
        return if (isSignIn) Destinations.DASHBOARD_ROUTE else Destinations.SURVEY_ROUTE
    }

    fun onForgotPassword() {
        TODO("Not yet implemented")
    }

    fun signInUser(
        username: String,
        password: String,
        showSnackbar: (String) -> Unit,
        onSuccess: (String) -> Unit
    ) {
        uiState = OnboardingUiState.Loading
        Firebase.firestore.collection("users").get().addOnSuccessListener { result ->
            val user = result.documents.first {
                it["username"].toString().lowercase() == username.lowercase()
            }
            val email = user["email"].toString()
            Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                onSuccess(if (user.get("hasUserCompletedSurvey") != null) Destinations.DASHBOARD_ROUTE else Destinations.SURVEY_ROUTE)

                uiState = OnboardingUiState.SignIn
            }.addOnFailureListener {
                showSnackbar("Oops! Unable to sign you in at the moment. ${it.localizedMessage}")
                uiState = OnboardingUiState.SignUp
            }
        }.addOnFailureListener {
            showSnackbar("Oops! Unable to sign you in at the moment. ${it.localizedMessage}")
            uiState = OnboardingUiState.SignUp
        }
    }

    fun updateGender(selectedItemIndex: Int) {

        surveyState = surveyState.copy(gender = selectedItemIndex)

    }

    fun updateAge(userAge: String) {
        surveyState = surveyState.copy(age = userAge)

    }

    fun showDatePicker(fragmentManager: FragmentManager) {
        val picker =
            MaterialDatePicker.Builder
                .datePicker()
                .setCalendarConstraints(
                    CalendarConstraints
                        .Builder()
                        .setValidator(
                            DateValidatorPointBackward.now()
                        )
                        .build()
                )
                .setSelection(surveyState.period)
                .build()
        picker.addOnPositiveButtonClickListener {
            surveyState = surveyState.copy(period = it)
        }
        picker.show(fragmentManager, picker.toString())
    }

    fun updateHeight(userHeight: String) {
        surveyState = surveyState.copy(height = userHeight)

    }

    fun updateWeight(userWeight: String) {
        surveyState = surveyState.copy(weight = userWeight)

    }

    fun updateLifestyle(updatedLifestyle: Int) {
        surveyState = surveyState.copy(lifestyle = updatedLifestyle)

    }

    fun onSignInActivityResult(
        isSignIn: Boolean,
        scope: CoroutineScope,
        result: ActivityResult,
        context: Context,
        snackbarHostState: SnackbarHostState,
        onNavigateToSurvey: () -> Unit,
        onNavigateToDashboard: () -> Unit
    ) {
        GoogleAuthService().linkWithFirebase(
            result.data,
            context,
            onSuccessListener = { firebaseUser ->
                if (!isSignIn) {
                    val db = Firebase.firestore.collection("users").document(firebaseUser.uid)
                    //TODO Check if username already exists
                    val user =
                        hashMapOf(
                            "email" to firebaseUser.email,
                            "username" to firebaseUser.displayName
                        )
                    db.set(user).addOnFailureListener {
                        scope.launch {
                            snackbarHostState.showSnackbar("Oops! Unable to register you at the moment. ${it.localizedMessage}")
                        }
                        uiState = OnboardingUiState.SignUp
                    }
                }
                scope.launch {
                    when (onContinueWithGoogle(isSignIn)) {
                        Destinations.SURVEY_ROUTE -> {
                            onNavigateToSurvey()
                        }

                        Destinations.DASHBOARD_ROUTE -> {
                            onNavigateToDashboard()
                        }

                        else -> {}
                    }
                }
            }) {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    suspend fun registerUser(
        username: String,
        email: String,
        password: String,
        showSnackbar: (String) -> Unit,
        onSuccess: () -> Unit
    ) {
        uiState = OnboardingUiState.Loading
        val isUsernameUnique = getFirestoreUsers().map { result ->
            !result.any { it["username"] == username }
        }
        if (isUsernameUnique.first()) {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val db = Firebase.firestore.collection("users").document(authResult.user!!.uid)
                    val user =
                        hashMapOf("username" to username, "password" to password, "email" to email)
                    db.set(user).addOnSuccessListener {
                        onSuccess()
                    }.addOnFailureListener {
                        showSnackbar("Oops! Unable to register you at the moment. ${it.localizedMessage}")
                        uiState = OnboardingUiState.SignUp
                    }
                }.addOnFailureListener {
                    showSnackbar("Oops! Unable to register you at the moment. ${it.localizedMessage}")
                    uiState = OnboardingUiState.SignUp
                }
        } else {
            showSnackbar("Oops! The username already exists.")
            uiState = OnboardingUiState.SignUp
        }
    }

    fun onNavigate(_uiState: OnboardingUiState) {
        uiState = _uiState
    }

    fun continueAsGuest(onSuccess: () -> Unit, showSnackbar: (String) -> Unit) {
        uiState = OnboardingUiState.Loading
        Firebase.auth.signInAnonymously().addOnSuccessListener {
            uiState = OnboardingUiState.NoUser
            onSuccess()
        }.addOnFailureListener {
            showSnackbar("Oops! Unable to log you in at the moment. ${it.localizedMessage}")
            uiState = OnboardingUiState.SignUp
        }
    }

    fun submitSurvey(context: Context, showSnackbar: (String) -> Unit, onSuccess: () -> Unit) {
        uiState = OnboardingUiState.Loading
        val user = Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid)
        val data = hashMapOf(
            "gender" to surveyState.gender,
            "age" to surveyState.age,
            "height" to surveyState.height,
            "weight" to surveyState.weight,
            "lifestyle" to surveyState.lifestyle,
            "diseases" to surveyState.diseases,
            "period" to surveyState.period
        )
        user.set(data, SetOptions.merge()).addOnSuccessListener {
            uiState = OnboardingUiState.SignIn
            viewModelScope.launch {
                context.dataStore.edit { settings ->
                    settings[IS_FIRST_RUN_KEY] = false
                }
                onSuccess()
            }
        }.addOnFailureListener {
            showSnackbar(it.localizedMessage ?: it.toString())
            uiState = OnboardingUiState.SignIn
        }
    }
}