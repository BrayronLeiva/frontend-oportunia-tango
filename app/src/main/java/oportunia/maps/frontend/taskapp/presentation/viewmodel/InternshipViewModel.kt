package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.repository.InternshipRepository
import javax.inject.Inject

/**
 * Sealed class representing the various states of an internship operation.
 */
sealed class InternshipState {
    /** Indicates an ongoing internship operation */
    data object Loading : InternshipState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val internships: List<Internship>) : InternshipState()

    /** Indicates no internships are available */
    data object Empty : InternshipState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : InternshipState()
}

/**
 * ViewModel responsible for managing location and internship-related UI state and business logic.
 *
 * @property locationCompanyRepository Repository interface for location operations
 * @property internshipLocationRepository Repository interface for internship-related operations
 */
@HiltViewModel
class InternshipViewModel @Inject constructor(
    private val internshipRepository: InternshipRepository
) : ViewModel() {

    private val _internshipState = MutableStateFlow<InternshipState>(InternshipState.Empty)
    val internshipState: StateFlow<InternshipState> = _internshipState

    private val _selectedInternship = MutableStateFlow<Internship?>(null)
    val selectedInternship: StateFlow<Internship?> = _selectedInternship

    private val _internshipsList = MutableStateFlow<List<Internship>>(emptyList())
    val internshipsList: StateFlow<List<Internship>> = _internshipsList

    private val _internships = MutableStateFlow<InternshipLocationState>(InternshipLocationState.Empty)
    val internships: StateFlow<InternshipLocationState> = _internships


    /**
     * Finds a location by its ID and updates the [location] state.
     *
     * @param locationId The ID of the location to find
     */
    fun selectInternshipById(internshipId: Long) {
        viewModelScope.launch {
            internshipRepository.findInternshipById(internshipId)
                .onSuccess { selectedInternship ->
                    _selectedInternship.value = selectedInternship
                }
                .onFailure { exception ->
                    Log.e("InternshipLocationViewModel", "Error fetching location by ID: ${exception.message}")
                }
        }
    }


    fun findAllInternships() {
        viewModelScope.launch {
            internshipRepository.findAllInternships()
                .onSuccess { internships ->
                    if (internships.isEmpty()) {
                        _internshipState.value = InternshipState.Empty
                    }else {
                        Log.d("InternshipViewModel", "Total Interships: ${internships.size}")
                        _internshipState.value = InternshipState.Success(internships)
                    }
                }
                .onFailure { exception ->
                    Log.e("InternshipViewModel", "Failed to fetch internships location: ${exception.message}")
                }
        }
    }


    /**
     * Retrieves internships for a specific location and updates the [internships] state.
     *
     * @param locationId The ID of the location to retrieve internships for
     */
    fun loadInternshipsByLocationId(locationId: Long) {
        viewModelScope.launch {
            _internships.value = InternshipLocationState.Loading
            internshipRepository.findInternshipsByLocationId(locationId)
                .onSuccess { internshipList ->
                    if (internshipList.isEmpty()) {
                        _internships.value = InternshipLocationState.Empty
                    } else {
                        _internships.value = InternshipLocationState.Success(internshipList)
                    }
                }
                .onFailure { exception ->
                    _internships.value = InternshipLocationState.Error("Failed to fetch internships with id $locationId: ${exception.message}")
                    Log.e("InternshipLocationViewModel", "Error fetching internships: ${exception.message}")
                }
        }
    }




}