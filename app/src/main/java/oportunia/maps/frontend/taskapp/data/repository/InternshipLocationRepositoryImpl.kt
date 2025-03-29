package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.datasource.internshiplocation.InternshipLocationDataSource
import oportunia.maps.frontend.taskapp.data.mapper.InternshipLocationMapper
import oportunia.maps.frontend.taskapp.domain.error.DomainError
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import kotlinx.coroutines.flow.first
import oportunia.maps.frontend.taskapp.data.mapper.InternshipMapper
import oportunia.maps.frontend.taskapp.domain.model.Internship
import java.io.IOException

class InternshipLocationRepositoryImpl(
    private val dataSource: InternshipLocationDataSource,
    private val internshipLocationMapper: InternshipLocationMapper,
    private val internshipMapper: InternshipMapper
) : InternshipLocationRepository {

    override suspend fun findAllInternshipLocations(): Result<List<InternshipLocation>> = runCatching {
        dataSource.getInternshipLocations().first().map { internshipLocationDto ->
            internshipLocationMapper.mapToDomain(internshipLocationDto)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch internship locations")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping internship locations")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findInternshipLocationById(id: Long): Result<InternshipLocation> = runCatching {
        val internshipLocationDto =
            dataSource.getInternshipLocationById(id) ?: throw DomainError.TaskError("Internship location not found")
        internshipLocationMapper.mapToDomain(internshipLocationDto)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch internship location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping internship location")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findInternshipsByLocationId(locationId: Long): Result<List<Internship>> = runCatching {
        // Fetch internships by locationId from the data source
        dataSource.getInternshipsByLocationId(locationId).first().map { internshipDto ->
            internshipMapper.mapToDomain(internshipDto)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch internships for location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping internships for location")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun saveInternshipLocation(internshipLocation: InternshipLocation): Result<Unit> = runCatching {
        dataSource.insertInternshipLocation(internshipLocationMapper.mapToDto(internshipLocation))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to save internship location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping internship location")
            else -> throw DomainError.TaskError("Failed to save internship location: ${throwable.message}")
        }
    }

    override suspend fun deleteInternshipLocation(id: Long): Result<Unit> = runCatching {
        val internshipLocation = dataSource.getInternshipLocationById(id)
            ?: throw DomainError.TaskError("Internship location not found")
        dataSource.deleteInternshipLocation(internshipLocation)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to delete internship location")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun updateInternshipLocation(internshipLocation: InternshipLocation): Result<Unit> = runCatching {
        dataSource.updateInternshipLocation(internshipLocationMapper.mapToDto(internshipLocation))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to update internship location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping internship location")
            else -> throw DomainError.TaskError("Failed to update internship location: ${throwable.message}")
        }
    }
}