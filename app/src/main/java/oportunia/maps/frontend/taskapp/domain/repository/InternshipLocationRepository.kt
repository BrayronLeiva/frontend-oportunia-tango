package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationRequestDto
import oportunia.maps.frontend.taskapp.domain.model.Internship
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
    suspend fun findRecommendedInternshipLocations(locationRequestDto: LocationRequestDto): Result<List<InternshipLocationRecommendedDto>>
    suspend fun findInternshipLocationsByLocationId(locationId: Long): Result<List<InternshipLocation>>
}