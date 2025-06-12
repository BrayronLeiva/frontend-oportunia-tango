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
import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.domain.model.Role
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

    private val _userRoleState = MutableStateFlow<UserRoleState>(UserRoleState.Empty)
    val userRoleState: StateFlow<UserRoleState> = _userRoleState

    private val _loggedInUser = MutableStateFlow<UserRole?>(null)
    val loggedInUser: StateFlow<UserRole?> = _loggedInUser


    fun saveUserRoleStudent(userId: Long) {
        viewModelScope.launch {
            val userRoleStudent = UserRoleCreateDto(1, userId)
            _userRoleState.value = UserRoleState.Loading
            Log.d("saveUserRoleStudent", "Creando UserRoleCreateDto con roleId=1 y userId=$userId")
            userRoleRepository.saveUserRole(userRoleStudent)
                .onSuccess { savedUserRole ->

                    _userRoleState.value = UserRoleState.Success(savedUserRole)
                    Log.e("UserRoleViewModel", "Saved succesfully user role")
                }
                .onFailure { exception ->
                    _userRoleState.value =
                        UserRoleState.Error(exception.printStackTrace().toString())
                    Log.e("UserRoleViewModel", "error$exception")
                }
        }
    }


    fun saveUserRoleCompany(userId: Long) {
        viewModelScope.launch {
            val userRoleCompany = UserRoleCreateDto(2, userId)
            _userRoleState.value = UserRoleState.Loading
            userRoleRepository.saveUserRole(userRoleCompany)
                .onSuccess { savedUserRole ->
                    _userRoleState.value = UserRoleState.Success(savedUserRole)
                    Log.e("UserRoleViewModel", "Saved succesfully user role")

                }
                .onFailure { exception ->
                    _userRoleState.value =
                        UserRoleState.Error(exception.printStackTrace().toString())
                    Log.e("UserRoleViewModel", "error$exception")
                }
        }
    }
}