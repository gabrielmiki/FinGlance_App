package miki.learn.finglance.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import miki.learn.finglance.R
import miki.learn.finglance.domain.model.SignInResult
import miki.learn.finglance.domain.model.UserData
import java.lang.Exception

class AccountRepository(
    val context: Context,
) : AccountRepositoryInterface {

    private val auth = Firebase.auth
    private val oneTapClient = Identity.getSignInClient(context)

    override suspend fun signUpEmailPassword(email: String, password: String): SignInResult {

        lateinit var signInResult: SignInResult

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {

                    val user = auth.currentUser
                    signInResult = SignInResult(
                        data = user?.run {
                            UserData(
                                userName = displayName,
                                profilePictureUrl = photoUrl?.toString()
                            )
                        },
                        errorMessage = null
                    )

                } else {
                    signInResult = SignInResult(
                        data = null,
                        errorMessage = task.exception.toString()
                    )
                }
            }.await()

        return signInResult
    }

    override suspend fun logInEmailPassword(email: String, password: String): SignInResult {

        var signInResult: SignInResult = SignInResult(data = null, errorMessage = null)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {

                    val user = auth.currentUser
                    signInResult = SignInResult(
                        data = user?.run {
                            UserData(
                                userName = email,
                                profilePictureUrl = photoUrl?.toString()
                            )
                        },
                        errorMessage = null
                    )
                } else {
                    signInResult = SignInResult(
                        data = null,
                        errorMessage = task.exception.toString()
                    )
                }
            }.await()

        return signInResult
    }

    override suspend fun signInIntent(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildGoogleSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override fun getSignedInUser(): UserData? =
        auth.currentUser?.run {
            UserData(
                userName = email,
                profilePictureUrl = photoUrl?.toString()
            )
        }

    /**
     * Google log in
     */
    private fun buildGoogleSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    override suspend fun getGoogleSignInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }
}