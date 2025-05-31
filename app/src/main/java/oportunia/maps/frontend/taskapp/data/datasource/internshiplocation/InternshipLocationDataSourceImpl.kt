package oportunia.maps.frontend.taskapp.data.datasource.internshiplocation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.data.mapper.InternshipLocationMapper
import oportunia.maps.frontend.taskapp.data.mapper.InternshipMapper

/**
 * InternshipLocationDataSource implementation.
 * @param internshipLocationMapper The mapper for converting between data and domain layer internship location objects.
 */

class InternshipLocationDataSourceImpl(
    private val internshipLocationMapper: InternshipLocationMapper,
    private val internshipMapper: InternshipMapper
) : InternshipLocationDataSource {

    override suspend fun getInternshipLocations(): Flow<List<InternshipLocationDto>> = flow {
        val internshipLocations = InternshipLocationProvider.findAllInternshipLocations()
        emit(internshipLocations.map { internshipLocationMapper.mapToDto(it) })
    }

    override suspend fun getInternshipLocationById(locationId: Long): InternshipLocationDto? =
        InternshipLocationProvider.findInternshipLocationById(locationId)?.let { internshipLocationMapper.mapToDto(it) }

    override suspend fun getInternshipsByLocationId(locationId: Long): Flow<List<InternshipDto>> = flow {
        val internships = InternshipLocationProvider.findAllInternshipsByLocationId(locationId)
        emit(internships.map { internshipMapper.mapToDto(it) })
    }

    override suspend fun insertInternshipLocation(internshipLocationDto: InternshipLocationDto) {
        val internshipLocation = internshipLocationMapper.mapToDomain(internshipLocationDto)
        InternshipLocationProvider.addInternshipLocation(internshipLocation)
    }

    override suspend fun updateInternshipLocation(internshipLocationDto: InternshipLocationDto) {
        val internshipLocation = internshipLocationMapper.mapToDomain(internshipLocationDto)
        InternshipLocationProvider.updateInternshipLocation(internshipLocation)
    }

    override suspend fun deleteInternshipLocation(internshipLocationDto: InternshipLocationDto) {
        val internshipLocation = internshipLocationMapper.mapToDomain(internshipLocationDto)
        InternshipLocationProvider.deleteInternshipLocation(internshipLocation)
    }
}