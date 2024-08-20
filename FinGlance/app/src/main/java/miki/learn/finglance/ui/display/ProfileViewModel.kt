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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import miki.learn.finglance.FinGlanceApplication
import miki.learn.finglance.data.repository.AccountRepository
import miki.learn.finglance.data.repository.AccountRepositoryInterface
import miki.learn.finglance.data.repository.RealtimeStorageRepository
import miki.learn.finglance.data.repository.UserDbRepositoryInterface
import miki.learn.finglance.domain.model.UserData

data class ProfileUiState(
    var userData: UserData? = null
)

class ProfileViewModel(
    private val userDbRepository: UserDbRepositoryInterface,
    val realtimeStorageRepository: RealtimeStorageRepository,
    val accountRepository: AccountRepositoryInterface
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FinGlanceApplication)
                val userDbRepository = application.container.userDbRepository
                val realtimeStorageRepository = application.container.realtimeStorageRepository
                val accountRepository = application.container.accountRepository
                ProfileViewModel(
                    userDbRepository = userDbRepository,
                    realtimeStorageRepository = realtimeStorageRepository,
                    accountRepository = accountRepository
                )
            }
        }
    }

    private var _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    var newUserName by mutableStateOf("")

    fun updateDisplayData(userData: UserData) {
        _uiState.update {
            it.copy(
                userData = UserData(
                    userName = userData.userName,
                    profilePictureUrl = ""
                )
            )
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(
                userData = UserData(
                    userName = newName,
                    profilePictureUrl = ""
                )
            )
        }
    }

    fun onSaveNewUserData() {
        val signedUser = accountRepository.getSignedInUser()

        realtimeStorageRepository.updateFirebaseUserData(
            userProfile = signedUser,
            newUserData = _uiState.value.userData
        )
    }

    suspend fun getUserDataFromRealtimeDatabase() {
        val signedUser = accountRepository.getSignedInUser()

        updateDisplayData(
            realtimeStorageRepository.getFirebaseUserData(signedUser)
        )
    }
}