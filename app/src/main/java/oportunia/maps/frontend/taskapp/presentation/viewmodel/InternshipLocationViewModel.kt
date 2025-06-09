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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationRequestDto
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.repository.InternshipRepository
import javax.inject.Inject

/**
 * Sealed class representing the various states of an internship operation.
 */
sealed class InternshipLocationState {
    /** Indicates an ongoing internship operation */
    data object Loading : InternshipLocationState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val internshipLocations: List<InternshipLocation>) : InternshipLocationState()

    /** Indicates no internships are available */
    data object Empty : InternshipLocationState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : InternshipLocationState()
}


sealed class InternshipLocationFlagState {
    /** Indicates an ongoing internship operation */
    data object Loading : InternshipLocationFlagState()

    /** Contains the successfully retrieved list of internships */
    data class Success(val internshipLocationsFlag: List<InternshipLocationFlagDto>) : InternshipLocationFlagState()

    /** Indicates no internships are available */
    data object Empty : InternshipLocationFlagState()

    /** Contains an error message if the internship operation fails */
    data class Error(val message: String) : InternshipLocationFlagState()
}


sealed class SaveInternshipResult {
    object Idle : SaveInternshipResult()
    object Saving : SaveInternshipResult()
    object Success : SaveInternshipResult()
    data class Error(val message: String) : SaveInternshipResult()
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
    private val internshipLocationRepository: InternshipLocationRepository,
    private val internshipRepository: InternshipRepository
) : ViewModel() {

    private val _internshipLocationStateFlag = MutableStateFlow<InternshipLocationFlagState>(InternshipLocationFlagState.Empty)
    val internshipLocationStateFlag: StateFlow<InternshipLocationFlagState> = _internshipLocationStateFlag

    private val _internshipLocationState = MutableStateFlow<InternshipLocationState>(InternshipLocationState.Empty)
    val internshipLocationState: StateFlow<InternshipLocationState> = _internshipLocationState

    private val _location = MutableStateFlow<LocationCompany?>(null)
    val location: StateFlow<LocationCompany?> = _location

    private val _internshipsLocationList = MutableStateFlow<List<InternshipLocation>>(emptyList())
    val internshipsLocationList: StateFlow<List<InternshipLocation>> = _internshipsLocationList

    private val _internshipsLocationRecommendedList = MutableStateFlow<List<InternshipLocationRecommendedDto>>(emptyList())
    val internshipsLocationRecommendedList: StateFlow<List<InternshipLocationRecommendedDto>> = _internshipsLocationRecommendedList

    private val _saveResult = MutableStateFlow<SaveInternshipResult>(SaveInternshipResult.Idle)
    val saveResult: StateFlow<SaveInternshipResult> = _saveResult
    /**
     * Finds a location by its ID and updates the [location] state.
     *
     * @param locationId The ID of the location to find
     */

    fun resetSaveResult() {
        _saveResult.value = SaveInternshipResult.Idle
    }
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
        _internshipLocationState.value = InternshipLocationState.Loading
        viewModelScope.launch {
            internshipLocationRepository.findAllInternshipLocations()
                .onSuccess { interLocations ->
                    if (interLocations.isEmpty()){
                        _internshipLocationState.value = InternshipLocationState.Empty
                    }else {
                        Log.d(
                            "InternshipLocationViewModel",
                            "Total Interships: ${interLocations.size}"
                        )
                        _internshipLocationState.value = InternshipLocationState.Success(interLocations)
                        _internshipsLocationList.value = interLocations
                    }
                }
                .onFailure { exception ->
                    Log.e("InternshipLocationViewModel", "Failed to fetch internships location: ${exception.message}")
                    _internshipLocationState.value = InternshipLocationState.Error("Failed to fetch internships locations: ${exception.message}")
                }
        }
    }

    fun loadInternShipsLocationsRecommended() {
        _internshipLocationState.value = InternshipLocationState.Loading
        viewModelScope.launch {
            try {
                Log.d("InternshipLocationViewModel", "Search By AI")
                //By the moment
                internshipLocationRepository.findRecommendedInternshipLocations(LocationRequestDto(9.940,-84.100))
                    .onSuccess { interLocations ->
                        if (interLocations.isEmpty()){
                            _internshipLocationState.value = InternshipLocationState.Empty
                        }else {
                            Log.d(
                                "InternshipLocationViewModel",
                                "Total Interships: ${interLocations.size}"
                            )
                            _internshipLocationState.value = InternshipLocationState.Success(emptyList())
                            _internshipsLocationRecommendedList.value = interLocations
                        }
                    }
                    .onFailure { exception ->
                        Log.e("InternshipLocationViewModel", "Error fetching internships locations: ${exception.message}")
                        _internshipLocationState.value = InternshipLocationState.Error("Failed to fetch internships locations: ${exception.message}")
                        //_internshipsLocationList.value = emptyList() // o lo que quieras mostrar en error
                    }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }


    /**
     * Retrieves internships for a specific location and updates the [internships] state.
     *
     * @param locationId The ID of the location to retrieve internships for
     */
    fun loadInternshipsLocationsByLocationId(locationId: Long) {
        viewModelScope.launch {
            _internshipLocationState.value = InternshipLocationState.Loading
            internshipLocationRepository.findInternshipLocationsByLocationId(locationId)
                .onSuccess { internshipsLocationList ->
                    if (internshipsLocationList.isEmpty()) {
                        _internshipLocationState.value = InternshipLocationState.Empty
                    } else {
                        Log.d("InternshipViewModel", "Total Interships: ${internshipsLocationList.size}")
                        _internshipLocationState.value = InternshipLocationState.Success(internshipsLocationList)
                    }
                }
                .onFailure { exception ->
                    _internshipLocationState.value = InternshipLocationState.Error("Failed to fetch internships with id $locationId: ${exception.message}")
                    Log.e("InternshipLocationViewModel", "Error fetching internships: ${exception.message}")
                }
        }
    }

    /**
     * Retrieves internships for a specific location and updates the [internships] state.
     *
     * @param locationId The ID of the location to retrieve internships for
     */
    fun loadInternshipsLocationsFlagByLocationId(locationId: Long) {
        viewModelScope.launch {
            _internshipLocationStateFlag.value = InternshipLocationFlagState.Loading
            internshipLocationRepository.findInternshipLocationsFlagByLocationId(locationId)
                .onSuccess { internshipsLocationList ->
                    if (internshipsLocationList.isEmpty()) {
                        _internshipLocationStateFlag.value = InternshipLocationFlagState.Empty
                    } else {
                        Log.d("InternshipViewModel", "Total Interships: ${internshipsLocationList.size}")
                        _internshipLocationStateFlag.value = InternshipLocationFlagState.Success(internshipsLocationList)
                    }
                }
                .onFailure { exception ->
                    _internshipLocationStateFlag.value = InternshipLocationFlagState.Error("Failed to fetch internships with id $locationId: ${exception.message}")
                    Log.e("InternshipLocationViewModel", "Error fetching internships: ${exception.message}")
                }
        }
    }

    fun saveInternshipLocation(internshipLocation: InternshipLocation){
        viewModelScope.launch {
            internshipLocationRepository.saveInternshipLocation(internshipLocation)
                .onSuccess {
                    Log.d("InternshipLocationViewModel", "Internship Location saved successfully")
                }
                .onFailure { exception ->
                    Log.e(
                        "InternshipLocationViewModel",
                        "Failed to save internship location: ${exception.message}"
                    )
                }
        }
        }

    fun addInternshipLocation(internshipLocation: InternshipLocation) {
        _internshipLocationState.update {
            val currentList = (it as? InternshipLocationState.Success)?.internshipLocations ?: emptyList()
            InternshipLocationState.Success(currentList + internshipLocation)
        }
    }

    fun saveInternshipWithLocation(
        internship: Internship,
        location: LocationCompany
    ) {
        viewModelScope.launch {
            _saveResult.value = SaveInternshipResult.Saving
            internshipRepository.saveInternship(internship)
                .onSuccess { savedInternship ->
                    val internshipLocation = InternshipLocation(
                        id = null,
                        internship = savedInternship,
                        location = location
                    )
                    internshipLocationRepository.saveInternshipLocation(internshipLocation)
                        .onSuccess {
                            addInternshipLocation(internshipLocation)
                            _saveResult.value = SaveInternshipResult.Success
                        }
                        .onFailure { e ->
                            _saveResult.value = SaveInternshipResult.Error("Failed to save internship location: ${e.message}")
                        }
                }
                .onFailure { e ->
                    _saveResult.value = SaveInternshipResult.Error("Failed to save internship: ${e.message}")
                }
        }
    }
}