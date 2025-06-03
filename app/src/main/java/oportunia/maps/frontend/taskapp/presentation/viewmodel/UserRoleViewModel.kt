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
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.domain.model.Role
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.domain.model.User
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

    private val _loggedInUser = MutableStateFlow<UserRole?>(null)
    val loggedInUser: StateFlow<UserRole?> = _loggedInUser

    /**
     * Finds a user role by email and updates the [userRole] state.
     *
     * @param email The email of the user whose role is to be fetched
     */
    private val _userRoleDraft = MutableStateFlow(
        UserRole(
            user = User(
                0L, "", "",
                lastName = "",
                enabled = false,
                tokenExpired = false,
                createDate = "",
                roles = emptyList(),
                password = ""
            ),
            Role(0L,TypeUser.STU)
        )
    )
    val userRoleDraft: StateFlow<UserRole> = _userRoleDraft


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _userRole.value = UserRoleState.Loading
            userRoleRepository.findAllUserRoles()
                .onSuccess { userRoles ->
                    // Filtra el usuario que coincide con el email y password
                    val loggedInUser = userRoles.firstOrNull { it.user.email == email && it.user.password == password }
                    if (loggedInUser != null) {
                        _userRole.value = UserRoleState.Success(loggedInUser)
                        _loggedInUser.value = loggedInUser
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

    fun saveUser() {
        viewModelScope.launch {
            val userRole = _userRoleDraft.value
            userRoleRepository.saveUserRole(userRole)
                .onSuccess { savedUserRole ->
                    // _registeredStudent.value = student
                    cleanStudentDraft()
                    Log.e("UserRoleViewModel", "Saved succesfully user role")
                }
                .onFailure { exception ->
                    Log.e("UserRoleViewModel", "Error saving user role")
                }
        }
    }


    private fun cleanStudentDraft(){
        _userRoleDraft.value = UserRole(
            user = User(
                0L, "", "",
                lastName = "",
                enabled = false,
                tokenExpired = false,
                createDate = "",
                roles = emptyList(),
                password = ""
            ),
            Role(0L,TypeUser.STU)
        )
    }



    fun updateUser(email: String, password: String) {
        val user = User(
            0L, email, password,
            lastName = "",
            enabled = false,
            tokenExpired = false,
            createDate = "",
            roles = emptyList(),
            password = ""
        )
        _userRoleDraft.value = _userRoleDraft.value.copy(user = user)
    }

    //fun updateRole() {
        //_userRoleDraft.value = _studentDraft.value.copy(rating = rating)
    //}
}