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

package com.manasmalla.ahamsvasth.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.manasmalla.ahamsvasth.R


class GoogleAuthService {

    fun loginUser(
        context: Context,
        signInIntentLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        signUpIntentLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        showSnackbar: (String)->Unit
    ) {
        val oneTapClient = Identity.getSignInClient(context.applicationContext);

        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(context.getString(R.string.your_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()
        Log.d("Progress", "Started")
        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener { result ->
            val intentSenderRequest =
                IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
            signInIntentLauncher.launch(intentSenderRequest)
        }.addOnFailureListener { exception ->
            val signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(context.getString(R.string.your_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .build()
            oneTapClient.beginSignIn(signUpRequest).addOnSuccessListener { result ->
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                signUpIntentLauncher.launch(intentSenderRequest)
            }.addOnFailureListener { exception ->
                showSnackbar("Error: "+exception.localizedMessage)
            }
        }
//        Firebase.auth.sign?
    }

    fun linkWithFirebase(data: Intent?, context: Context, onSuccessListener: (FirebaseUser) -> Unit, showSnackbar: (String) -> Unit) {
        try {
            val oneTapClient = Identity.getSignInClient(context.applicationContext);
            val credential: SignInCredential =
                oneTapClient.getSignInCredentialFromIntent(data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                // Got an ID token from Google. Use it to authenticate
                // with your backend.
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                Firebase.auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val user = Firebase.auth.currentUser
                        if (user != null) {
                            onSuccessListener(user)
                        }else{
                            showSnackbar("Oops! No user found.")
                        }
                    }else{
                        task.exception?.localizedMessage?.let { showSnackbar("Error: $it") }
                    }
                }
            }
        } catch (e: ApiException) {
            showSnackbar("Error: ${e.localizedMessage}")
        }
    }
}