package oportunia.maps.frontend.taskapp.data.datasource.internshiplocation

import kotlinx.coroutines.flow.Flow
import oportunia.maps.frontend.taskapp.data.datasource.model.InternshipDto
import oportunia.maps.frontend.taskapp.data.datasource.model.InternshipLocationDto

/**
 * This interface represents the InternshipLocationRepository.
 */
interface InternshipLocationDataSource {
    suspend fun getInternshipLocations(): Flow<List<InternshipLocationDto>>
    suspend fun getInternshipLocationById(locationId: Long): InternshipLocationDto?
    suspend fun getInternshipsByLocationId(locationId: Long): Flow<List<InternshipDto>>
    suspend fun insertInternshipLocation(internshipLocationDto: InternshipLocationDto)
    suspend fun deleteInternshipLocation(internshipLocationDto: InternshipLocationDto)
    suspend fun updateInternshipLocation(internshipLocationDto: InternshipLocationDto)
}