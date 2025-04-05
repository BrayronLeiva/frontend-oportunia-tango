package oportunia.maps.frontend.taskapp.data.datasource.internshiplocation

import kotlinx.coroutines.flow.Flow
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto

/**
 * This interface represents the InternshipLocationRepository.
 */
interface InternshipLocationDataSource {
    suspend fun getInternshipLocations(): Flow<List<InternshipLocationDto>>
    suspend fun getInternshipLocationById(locationId: Long): InternshipLocationDto?
    suspend fun getInternshipsByLocationId(locationId: Long): Flow<List<oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto>>
    suspend fun insertInternshipLocation(internshipLocationDto: InternshipLocationDto)
    suspend fun deleteInternshipLocation(internshipLocationDto: InternshipLocationDto)
    suspend fun updateInternshipLocation(internshipLocationDto: InternshipLocationDto)
}