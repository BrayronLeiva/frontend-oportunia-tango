package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedFlagDto
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation

/**
 * This interface represents the InternshipLocationRepository.
 */
interface InternshipLocationRepository {
    suspend fun findAllInternshipLocations(): Result<List<InternshipLocation>>
    suspend fun findInternshipLocationById(id: Long): Result<InternshipLocation>
    //suspend fun findInternshipsByLocationId(locationId: Long): Result<List<Internship>>
    suspend fun saveInternshipLocation(internshipLocation: InternshipLocation): Result<Unit>
    suspend fun deleteInternshipLocation(id: Long): Result<Unit>
    suspend fun updateInternshipLocation(internshipLocation: InternshipLocation): Result<Unit>
    suspend fun findRecommendedInternshipLocations(): Result<List<InternshipLocationRecommendedDto>>
    suspend fun findInternshipLocationsByLocationId(locationId: Long): Result<List<InternshipLocation>>
    suspend fun findInternshipLocationsFlagByLocationId(locationId: Long): Result<List<InternshipLocationFlagDto>>
    suspend fun findAllFlagInternshipLocations(): Result<List<InternshipLocationFlagDto>>
    suspend fun findRecommendedInternshipLocationsFlag(): Result<List<InternshipLocationRecommendedFlagDto>>
    suspend fun findAllInternshipLocationsAvailable(): Result<List<InternshipLocation>>
    suspend fun findRecommendedInternshipLocationsAvailable(): Result<List<InternshipLocationRecommendedDto>>
}