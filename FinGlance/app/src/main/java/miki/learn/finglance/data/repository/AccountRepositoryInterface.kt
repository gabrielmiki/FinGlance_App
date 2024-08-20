package miki.learn.finglance.data.repository

import android.content.Intent
import android.content.IntentSender
import miki.learn.finglance.domain.model.SignInResult
import miki.learn.finglance.domain.model.UserData

interface AccountRepositoryInterface {

    suspend fun signUpEmailPassword(email: String, password: String): SignInResult

    suspend fun logInEmailPassword(email: String, password: String): SignInResult

    fun getSignedInUser(): UserData?

    suspend fun getGoogleSignInWithIntent(intent: Intent): SignInResult

    suspend fun signInIntent(): IntentSender?

    suspend fun signOut()

}