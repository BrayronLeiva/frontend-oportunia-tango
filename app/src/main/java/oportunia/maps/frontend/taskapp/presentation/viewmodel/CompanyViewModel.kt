package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import oportunia.maps.frontend.taskapp.domain.model.Company
import oportunia.maps.frontend.taskapp.domain.repository.CompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CompanyState {
    object Loading : CompanyState()
    data class Success(val company: Company) : CompanyState()
    object Empty : CompanyState()
    object Failure : CompanyState()
    data class Error(val message: String) : CompanyState()
}

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val companyRepository: CompanyRepository
) : ViewModel() {

    private val _companyState = MutableStateFlow<CompanyState>(CompanyState.Empty)
    val companyState: StateFlow<CompanyState> = _companyState

    private val _loggedCompany = MutableStateFlow<Company?>(null)
    val loggedCompany: StateFlow<Company?> = _loggedCompany

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
}