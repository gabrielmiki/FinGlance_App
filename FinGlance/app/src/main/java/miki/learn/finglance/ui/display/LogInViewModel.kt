package miki.learn.finglance.ui.display

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import miki.learn.finglance.FinGlanceApplication
import miki.learn.finglance.data.repository.AccountRepositoryInterface
import miki.learn.finglance.data.repository.RealtimeStorageRepository
import miki.learn.finglance.domain.model.SignInResult
import miki.learn.finglance.domain.model.UserData

data class LogInUiState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)

class LogInViewModel(
    val accountRepository: AccountRepositoryInterface,
    val realtimeStorageRepository: RealtimeStorageRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinGlanceApplication)
                val accountRepository = application.container.accountRepository
                val realtimeStorageRepository = application.container.realtimeStorageRepository
                LogInViewModel(
                    accountRepository = accountRepository,
                    realtimeStorageRepository = realtimeStorageRepository
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(LogInUiState())
    val uiState = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateEmailDisplay(email: String) {
        this.email = email
    }
    fun updatePasswordDisplay(password: String) {
        this.password = password
    }

    fun onSignInResult(result: SignInResult) {
        _uiState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage,
            )
        }
    }

    suspend fun logInUser() {
        val result = accountRepository.logInEmailPassword(email, password)

        onSignInResult(result)
    }

    fun resetState() {
        _uiState.update { LogInUiState() }
    }
}