package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.repository.AuthRepository
import javax.inject.Inject

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    object Empty : UserState()
    object Failure : UserState()
    data class Error(val message: String) : UserState()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Empty)
    val userState: StateFlow<UserState> = _userState

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _userState.value = UserState.Loading

            val authResult = authRepository.login(email, password)

            if (authResult.isSuccess) {
                // Proceed to get the user info
                userRepository.findAllUsers()
                    .onSuccess { users ->
                        val user = users.find { it.email == email }
                        if (user != null) {
                            _userState.value = UserState.Success(user)
                            _loggedInUser.value = user
                        } else {
                            _userState.value = UserState.Failure
                        }
                    }
                    .onFailure { exception ->
                        _userState.value = UserState.Error("Failed to fetch user info: ${exception.message}")
                    }
            } else {
                val exception = authResult.exceptionOrNull()
                _userState.value = UserState.Error("Login failed: ${exception?.message ?: "Unknown error"}")
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            authRepository.logout()
            _userState.value = UserState.Empty
            _loggedInUser.value = null
        }
    }

    fun isUserAuthenticated(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.isAuthenticated()
            onResult(result.getOrDefault(false))
        }
    }

    fun saveUser() {
        viewModelScope.launch {
            val user = _loggedInUser.value ?: return@launch
            userRepository.saveUser(user)
                .onSuccess {
                    Log.i("UserViewModel", "User saved successfully")
                }
                .onFailure { exception ->
                    Log.e("UserViewModel", "Error saving user: ${exception.message}")
                }
        }
    }
}
