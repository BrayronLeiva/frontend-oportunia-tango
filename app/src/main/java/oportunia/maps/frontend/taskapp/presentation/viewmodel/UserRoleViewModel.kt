package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import oportunia.maps.frontend.taskapp.domain.model.UserRole
import oportunia.maps.frontend.taskapp.domain.repository.UserRoleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed class representing the various states of a user role operation.
 */
sealed class UserRoleState {
    /** Indicates an ongoing user role operation */
    data object Loading : UserRoleState()

    /** Contains the successfully retrieved user role */
    data class Success(val userRole: UserRole) : UserRoleState()

    /** Indicates no user role is available */
    data object Empty : UserRoleState()

    /** Indicates credentials doesn't exit's */
    data object Failure : UserRoleState()

    /** Contains an error message if the user role operation fails */
    data class Error(val message: String) : UserRoleState()
}

/**
 * ViewModel responsible for managing user role-related UI state and business logic.
 *
 * @property userRoleRepository Repository interface for user role operations
 */
@HiltViewModel
class UserRoleViewModel @Inject constructor(
    private val userRoleRepository: UserRoleRepository
) : ViewModel() {

    private val _userRole = MutableStateFlow<UserRoleState>(UserRoleState.Empty)
    val userRole: StateFlow<UserRoleState> = _userRole

    /**
     * Finds a user role by email and updates the [userRole] state.
     *
     * @param email The email of the user whose role is to be fetched
     */


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _userRole.value = UserRoleState.Loading
            userRoleRepository.findAllUserRoles()
                .onSuccess { userRoles ->
                    // Filtra el usuario que coincide con el email y password
                    val loggedInUser = userRoles.firstOrNull { it.user.email == email && it.user.password == password }
                    if (loggedInUser != null) {
                        _userRole.value = UserRoleState.Success(loggedInUser)
                    } else {
                        _userRole.value = UserRoleState.Failure
                    }
                }
                .onFailure  { exception ->
                    Log.e("UserRoleViewModel", "Failed to fetch userRoles: ${exception.message}")
                }
        }
    }
    /*
    fun loadUserRoleByEmail(email: String) {
        viewModelScope.launch {
            _userRole.value = UserRoleState.Loading
            userRoleRepository.getUserRoleByEmail(email)
                .onSuccess { role ->
                    if (role != null) {
                        _userRole.value = UserRoleState.Success(role)
                    } else {
                        _userRole.value = UserRoleState.Empty
                    }
                }
                .onFailure { exception ->
                    _userRole.value = UserRoleState.Error("Failed to fetch user role: ${exception.message}")
                    Log.e("UserRoleViewModel", "Error fetching user role: ${exception.message}")
                }
        }
    }
    */
}