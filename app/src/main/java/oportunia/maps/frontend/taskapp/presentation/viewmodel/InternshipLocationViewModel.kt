package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.model.Task

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
class InternshipLocationViewModel(
    private val locationCompanyRepository: LocationCompanyRepository,
    private val internshipLocationRepository: InternshipLocationRepository
) : ViewModel() {

    private val _location = MutableStateFlow<LocationCompany?>(null)
    val location: StateFlow<LocationCompany?> = _location

    private val _internships = MutableStateFlow<InternshipState>(InternshipState.Empty)
    val internships: StateFlow<InternshipState> = _internships

    private val _internshipsLocationList = MutableStateFlow<List<InternshipLocation>>(emptyList())
    val internshipsLocationList: StateFlow<List<InternshipLocation>> = _internshipsLocationList

    /**
     * Finds a location by its ID and updates the [location] state.
     *
     * @param locationId The ID of the location to find
     */
    fun selectInternshipLocationById(locationId: Long) {
        viewModelScope.launch {
            locationCompanyRepository.findLocationById(locationId)
                .onSuccess { location ->
                    _location.value = location
                    loadInternshipsByLocationId(locationId)  // Load internships when location is found
                }
                .onFailure { exception ->
                    Log.e("InternshipLocationViewModel", "Error fetching location by ID: ${exception.message}")
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
            _internships.value = InternshipState.Loading
            internshipLocationRepository.findInternshipsByLocationId(locationId)
                .onSuccess { internshipList ->
                    if (internshipList.isEmpty()) {
                        _internships.value = InternshipState.Empty
                    } else {
                        _internships.value = InternshipState.Success(internshipList)
                    }
                }
                .onFailure { exception ->
                    _internships.value = InternshipState.Error("Failed to fetch internships: ${exception.message}")
                    Log.e("InternshipLocationViewModel", "Error fetching internships: ${exception.message}")
                }
        }
    }

    fun findAllInternShipsLocations() {
        viewModelScope.launch {
            internshipLocationRepository.findAllInternshipLocations()
                .onSuccess { interLocations ->
                    Log.d("TaskViewModel", "Total Interships: ${interLocations.size}")
                    _internshipsLocationList.value = interLocations
                }
                .onFailure { exception ->
                    Log.e("TaskViewModel", "Failed to fetch tasks: ${exception.message}")
                }
        }
    }
}