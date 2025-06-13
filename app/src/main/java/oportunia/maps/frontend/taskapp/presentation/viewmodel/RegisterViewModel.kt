package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterStudentDto
import oportunia.maps.frontend.taskapp.domain.repository.RegisterRepository
import java.io.File
import javax.inject.Inject

sealed class RegisterState<out T> {
    object Loading : RegisterState<Nothing>()
    data class Success<out T>(val data: T) : RegisterState<T>()
    object Empty : RegisterState<Nothing>()
    object Failure : RegisterState<Nothing>()
    data class Error(val message: String) : RegisterState<Nothing>()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerStudentState = MutableStateFlow<RegisterState<RegisterStudentDto>>(RegisterState.Empty)
    val registerStudentState: StateFlow<RegisterState<RegisterStudentDto>> = _registerStudentState

    private val _registerCompanyState = MutableStateFlow<RegisterState<RegisterCompanyDto>>(RegisterState.Empty)
    val registerCompanyState: StateFlow<RegisterState<RegisterCompanyDto>> = _registerCompanyState

    private val _studentImageUploadState = MutableStateFlow<RegisterState<String>>(RegisterState.Empty)
    val studentImageUploadState: StateFlow<RegisterState<String>> = _studentImageUploadState

    private val _companyImageUploadState = MutableStateFlow<RegisterState<String>>(RegisterState.Empty)
    val companyImageUploadState: StateFlow<RegisterState<String>> = _companyImageUploadState

    fun registerStudent(student: RegisterStudentCreateDto) {
        _registerStudentState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = registerRepository.saveStudent(student)
            result.fold(
                onSuccess = {
                    _registerStudentState.value = RegisterState.Success(it)
                    Log.i("RegisterViewModel", "Student registered successfully")
                },
                onFailure = { e ->
                    _registerStudentState.value = RegisterState.Error(e.message ?: "Unknown error")
                    Log.e("RegisterViewModel", "Student registration failed: ${e.message}")
                }
            )
        }
    }

    fun registerCompany(company: RegisterCompanyCreateDto) {
        _registerCompanyState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = registerRepository.saveCompany(company)
            result.fold(
                onSuccess = {
                    _registerCompanyState.value = RegisterState.Success(it)
                    Log.i("RegisterViewModel", "Company registered successfully")
                },
                onFailure = { e ->
                    _registerCompanyState.value = RegisterState.Error(e.message ?: "Unknown error")
                    Log.e("RegisterViewModel", "Company registration failed: ${e.message}")
                }
            )
        }
    }

    fun uploadStudentImage(studentId: Long, file: File) {
        _studentImageUploadState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = registerRepository.uploadProfileImageStudent(studentId, file)
            result.fold(
                onSuccess = { responseMap ->
                    val imageUrl = responseMap["imageUrl"] ?: ""
                    _studentImageUploadState.value = RegisterState.Success(imageUrl)
                    Log.i("RegisterViewModel", "Student image uploaded: $imageUrl")
                },
                onFailure = { e ->
                    _studentImageUploadState.value = RegisterState.Error(e.message ?: "Unknown error")
                    Log.e("RegisterViewModel", "Student image upload failed: ${e.message}")
                }
            )
        }
    }

    fun uploadCompanyImage(companyId: Long, file: File) {
        _companyImageUploadState.value = RegisterState.Loading
        viewModelScope.launch {
            val result = registerRepository.uploadProfileImageCompany(companyId, file)
            result.fold(
                onSuccess = { responseMap ->
                    val imageUrl = responseMap["imageUrl"] ?: ""
                    _companyImageUploadState.value = RegisterState.Success(imageUrl)
                    Log.i("RegisterViewModel", "Company image uploaded: $imageUrl")
                },
                onFailure = { e ->
                    _companyImageUploadState.value = RegisterState.Error(e.message ?: "Unknown error")
                    Log.e("RegisterViewModel", "Company image upload failed: ${e.message}")
                }
            )
        }
    }
}