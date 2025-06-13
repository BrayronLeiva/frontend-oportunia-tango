package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.Company
import oportunia.maps.frontend.taskapp.domain.repository.CompanyRepository
import javax.inject.Inject

sealed class CompanyState {
    object Loading : CompanyState()
    data class Success(val company: Company) : CompanyState()
    object Empty : CompanyState()
    object Failure : CompanyState()
    data class Error(val message: String) : CompanyState()
}

sealed class CompanyImageState {
    object Loading : CompanyImageState()
    data class Success(val imageUrl: String) : CompanyImageState()
    object Empty : CompanyImageState()
    object Failure : CompanyImageState()
    data class Error(val message: String) : CompanyImageState()
}

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
) : ViewModel() {

    private val _selectedCompany = MutableStateFlow<Company?>(null)
    val selectedCompany: StateFlow<Company?> = _selectedCompany

    private val _companyState = MutableStateFlow<CompanyState>(CompanyState.Empty)
    val companyState: StateFlow<CompanyState> = _companyState

    private val _loggedCompany = MutableStateFlow<Company?>(null)
    val loggedCompany: StateFlow<Company?> = _loggedCompany

    private val _companyImageState = MutableStateFlow<CompanyImageState>(CompanyImageState.Empty)
    val companyImageState: StateFlow<CompanyImageState> = _companyImageState

    fun getLoggedCompany() {
        viewModelScope.launch {
            _companyState.value = CompanyState.Loading
            val result = companyRepository.loggedCompany()
            result.fold(
                onSuccess = {
                    _loggedCompany.value = it
                    _companyState.value = CompanyState.Success(it)
                },
                onFailure = { e ->
                    Log.e("CompanyViewModel", "Failed to load company: ${e.message}")
                    _companyState.value = CompanyState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }

    fun findCompanyById(companyId: Long) {
        viewModelScope.launch {
            _companyState.value = CompanyState.Loading
            val result = companyRepository.findCompanyById(companyId)
            result.fold(
                onSuccess = {
                    _companyState.value = CompanyState.Success(it)
                    _selectedCompany.value = it
                },
                onFailure = { e ->
                    Log.e("CompanyViewModel", "Failed to load company: ${e.message}")
                    _companyState.value = CompanyState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }

    fun registerCompany(company: Company){
        viewModelScope.launch {
            _companyState.value = CompanyState.Loading
            val result = companyRepository.saveCompany(company)
            result.fold(
                onSuccess = {
                    _companyState.value = CompanyState.Success(it)
                    Log.e("CompanyViewModel", "Company registered successfully")
                },
                onFailure = { e ->
                    Log.e("CompanyViewModel", "Failed to load company: ${e.message}")
                    _companyState.value = CompanyState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }
    /*
    fun uploadImage(companyId: Long, file: File) {
        _companyImageState.value = CompanyImageState.Loading
        viewModelScope.launch {
            companyRepository.uploadProfileImage(companyId, file)
                .onSuccess { responseMap ->
                    val responseText = responseMap.toString()
                    val regex = Regex("imageUrl=([^}]+)")
                    val match = regex.find(responseText)
                    val imageUrl1 = match?.groupValues?.get(1) ?: ""
                    Log.e("CompanyViewModel", "Image 1: $imageUrl1")

                    val imageUrl2 = responseMap["imageUrl"]?.toString() ?: ""
                    Log.e("CompanyViewModel", "Image 2: $imageUrl2")

                    _companyImageState.value = CompanyImageState.Success(imageUrl2)
                }
                .onFailure { exception ->
                    Log.e("CompanyViewModel", "Error uploading image")
                    _companyImageState.value = CompanyImageState.Error(exception.toString())
                }
        }
    }*/
}