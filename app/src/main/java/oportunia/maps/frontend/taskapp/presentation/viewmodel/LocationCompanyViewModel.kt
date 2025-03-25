package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Sealed class representing the various states of a location operation.
 */
sealed class LocationState {
    /** Indicates an ongoing location operation */
    data object Loading : LocationState()

    /** Contains the successfully retrieved location */
    data class Success(val location: LocationCompany) : LocationState()

    /** Indicates no location is available */
    data object Empty : LocationState()

    /** Contains an error message if the location operation fails */
    data class Error(val message: String) : LocationState()
}

/**
 * ViewModel responsible for managing location-related UI state and business logic.
 *
 * @property repository Repository interface for location operations
 */
class LocationCompanyViewModel(
    private val repository: LocationCompanyRepository
) : ViewModel() {

    private val _location = MutableStateFlow<LocationState>(LocationState.Empty)
    val location: StateFlow<LocationState> = _location

    private val _selectedLocation = MutableStateFlow<LocationCompany?>(null)
    val selectedLocation: StateFlow<LocationCompany?> = _selectedLocation

    private val _locationList = MutableStateFlow<List<LocationCompany>>(emptyList())
    val locationList: StateFlow<List<LocationCompany>> = _locationList

    /**
     * Finds a location by its ID and updates the [selectedLocation] state.
     *
     * @param locationId The ID of the location to find
     */
    fun selectLocationById(locationId: Long) {
        viewModelScope.launch {
            repository.findLocationById(locationId)
                .onSuccess { location ->
                    _selectedLocation.value = location
                }
                .onFailure { exception ->
                    Log.e("LocationViewModel", "Error fetching location by ID: ${exception.message}")
                }
        }
    }

    /**
     * Retrieves all available locations and updates the [locationList] state.
     * Should be called when the ViewModel is initialized or when a refresh is needed.
     */
    fun findAllLocations() {
        viewModelScope.launch {
            repository.findAllLocations()
                .onSuccess { locations ->
                    Log.d("LocationViewModel", "Total Locations: ${locations.size}")
                    _locationList.value = locations
                }
                .onFailure { exception ->
                    Log.e("LocationViewModel", "Failed to fetch locations: ${exception.message}")
                }
        }
    }
}
