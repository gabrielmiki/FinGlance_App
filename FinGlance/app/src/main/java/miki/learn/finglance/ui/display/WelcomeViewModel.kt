package miki.learn.finglance.ui.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import miki.learn.finglance.FinGlanceApplication
import miki.learn.finglance.data.repository.AccountRepositoryInterface
import miki.learn.finglance.domain.model.SignInResult

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

class WelcomeViewModel(
    val accountRepository: AccountRepositoryInterface
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinGlanceApplication)
                val accountRepository = application.container.accountRepository
                WelcomeViewModel(accountRepository = accountRepository)
            }
        }
    }

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _uiState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _uiState.update { SignInState() }
    }
}