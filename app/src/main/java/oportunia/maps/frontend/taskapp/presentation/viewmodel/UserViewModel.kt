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
import oportunia.maps.frontend.taskapp.data.remote.dto.UserCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.domain.model.Role
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.domain.model.UserRole
import oportunia.maps.frontend.taskapp.domain.repository.AuthRepository
import javax.inject.Inject

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    object Empty : UserState()
    object Failure : UserState()
    data class Error(val message: String) : UserState()
}

sealed class UserCreateState {
    /** Indicates an ongoing task operation */
    data object Loading : UserCreateState()

    /** Contains the successfully retrieved task */
    data class Success(val user: User) : UserCreateState()

    /** Indicates no task is available */
    data object Empty : UserCreateState()

    /** Contains an error message if the task operation fails */
    data class Error(val message: String) : UserCreateState()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Empty)
    val userState: StateFlow<UserState> = _userState

    private val _userCreateState = MutableStateFlow<UserCreateState>(UserCreateState.Empty)
    val userCreateState: StateFlow<UserCreateState> = _userCreateState

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

    fun saveUser2() {
        viewModelScope.launch {
            val user = _userDraft.value
            _userCreateState.value = UserCreateState.Loading
            userRepository.saveUser(user)
                .onSuccess { savedUser ->
                    // _registeredStudent.value = student
                    //cleanDraft()
                    _userCreateState.value = UserCreateState.Success(savedUser)
                    Log.e("UserViewModel", "Saved succesfully user role")
                }
                .onFailure { exception ->
                    _userCreateState.value = UserCreateState.Error("Error")
                    Log.e("UserViewModel", "Error saving user role")
                }
        }
    }



    //Structures to save the user
    private val _userDraft = MutableStateFlow(
        UserCreateDto(
            firstName = "",
            lastName = "",
            email = "",
            password = "",
            enable = false
        )
    )
    val userDraft: StateFlow<UserCreateDto> = _userDraft

    fun updateEmail(email: String) {
        _userDraft.value = _userDraft.value.copy(email = email)
    }
    fun updatePassword(password: String) {
        _userDraft.value = _userDraft.value.copy(password = password)
    }
    fun updateFirstName(firstName: String) {
        _userDraft.value = _userDraft.value.copy(firstName = firstName)
    }
    fun updateLastName(lastName: String) {
        _userDraft.value = _userDraft.value.copy(lastName = lastName)
    }


}
