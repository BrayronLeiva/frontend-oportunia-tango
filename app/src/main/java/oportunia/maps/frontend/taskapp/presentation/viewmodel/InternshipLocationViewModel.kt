package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import javax.inject.Inject

/**
 * Sealed class representing the various states of an internship operation.
 */
sealed class InternshipLocationState {
    /** Indicates an ongoing internship operation */
    data object Loading : InternshipLocationState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val internships: List<InternshipLocation>) : InternshipLocationState()

    /** Indicates no internships are available */
    data object Empty : InternshipLocationState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : InternshipLocationState()
}

/**
 * ViewModel responsible for managing location and internship-related UI state and business logic.
 *
 * @property locationCompanyRepository Repository interface for location operations
 * @property internshipLocationRepository Repository interface for internship-related operations
 */
@HiltViewModel
class InternshipLocationViewModel @Inject constructor(
    private val locationCompanyRepository: LocationCompanyRepository,
    private val internshipLocationRepository: InternshipLocationRepository
) : ViewModel() {

    private val _location = MutableStateFlow<LocationCompany?>(null)
    val location: StateFlow<LocationCompany?> = _location


    private val _internshipsLocationList = MutableStateFlow<List<InternshipLocation>>(emptyList())
    val internshipsLocationList: StateFlow<List<InternshipLocation>> = _internshipsLocationList

    private val _internshipsLocationRecommendedList = MutableStateFlow<List<InternshipLocationRecommendedDto>>(emptyList())
    val internshipsLocationRecommendedList: StateFlow<List<InternshipLocationRecommendedDto>> = _internshipsLocationRecommendedList


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
                    //loadInternshipsByLocationId(locationId)  // Load internships when location is found
                }
                .onFailure { exception ->
                    Log.e("InternshipLocationViewModel", "Error fetching location by ID: ${exception.message}")
                }
        }
    }


    fun findAllInternShipsLocations() {
        viewModelScope.launch {
            internshipLocationRepository.findAllInternshipLocations()
                .onSuccess { interLocations ->
                    Log.d("InternshipLocationViewModel", "Total Interships: ${interLocations.size}")
                    _internshipsLocationList.value = interLocations
                }
                .onFailure { exception ->
                    Log.e("InternshipLocationViewModel", "Failed to fetch internships location: ${exception.message}")
                }
        }
    }

    fun loadInternShipsLocationsRecommended() {
        viewModelScope.launch {
            try {
                Log.d("InternshipLocationViewModel", "Search By AI")
                internshipLocationRepository.findRecommendedInternshipLocations()
                    .onSuccess { interLocations ->
                        Log.d("InternshipLocationViewModel", "Total Interships: ${interLocations.size}")
                        _internshipsLocationRecommendedList.value = interLocations
                    }
                    .onFailure { exception ->
                        Log.e("InternshipLocationViewModel", "Error fetching internships: ${exception.message}")
                        //_internshipsLocationList.value = emptyList() // o lo que quieras mostrar en error
                    }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}