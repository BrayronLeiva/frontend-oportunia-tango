package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
@HiltViewModel
class LocationCompanyViewModel @Inject constructor(
    private val repository: LocationCompanyRepository
) : ViewModel() {

    private val _location = MutableStateFlow<LocationState>(LocationState.Empty)
    val location = _location.asStateFlow()

    private val _selectedLocation = MutableStateFlow<LocationCompany?>(null)
    val selectedLocation = _selectedLocation.asStateFlow()

    private val _locationList = MutableStateFlow<List<LocationCompany>>(emptyList())
    val locationList = _locationList.asStateFlow()

    /**
     * Finds a location by its ID and updates the [selectedLocation] state.
     *
     * @param locationId The ID of the location to find
     */
    fun selectLocationById(locationId: Long) {
        viewModelScope.launch {
            _location.value = LocationState.Loading
            repository.findLocationById(locationId)
                .onSuccess { location ->
                    _location.value = LocationState.Success(location)
                    _selectedLocation.value = location
                }
                .onFailure { exception ->
                    _location.value = LocationState.Error(exception.message ?: "Unknown error")
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
            _location.value = LocationState.Loading
            repository.findAllLocations()
                .onSuccess { locations ->
                    _locationList.value = locations
                    _location.value =
                        if (locations.isEmpty()) LocationState.Empty else LocationState.Empty
                }
                .onFailure { exception ->
                    _location.value = LocationState.Error(exception.message ?: "Unknown error")
                    Log.e("LocationViewModel", "Failed to fetch locations: ${exception.message}")
                }
        }
    }
}
