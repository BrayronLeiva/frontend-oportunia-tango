package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.datasource.internshiplocation.InternshipLocationDataSource
import oportunia.maps.frontend.taskapp.data.mapper.InternshipLocationMapper
import oportunia.maps.frontend.taskapp.domain.error.DomainError
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import kotlinx.coroutines.flow.first
import oportunia.maps.frontend.taskapp.data.mapper.InternshipMapper
import oportunia.maps.frontend.taskapp.data.remote.InternshipLocationRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class InternshipLocationRepositoryImpl  @Inject constructor(
    private val remoteDataSource: InternshipLocationRemoteDataSource,
    private val internshipLocationMapper: InternshipLocationMapper,
    private val internshipMapper: InternshipMapper
) : InternshipLocationRepository {


    override suspend fun findAllInternshipLocations(): Result<List<InternshipLocation>> {
        return try {
            remoteDataSource.getAll().map { dtos ->
                dtos.map { internshipLocationMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching location companies: ${e.message}"))
        }
    }

    /**
     * Retrieves a location company by its ID.
     */
    override suspend fun findInternshipLocationById(id: Long): Result<InternshipLocation> {
        return remoteDataSource.getById(id).map {
            internshipLocationMapper.mapToDomain(it)
        }
    }


    /**
     * Creates a new location company.
     */
    override suspend fun saveInternshipLocation(internshipLocation: InternshipLocation): Result<Unit> {
        return remoteDataSource.create(internshipLocationMapper.mapToDto(internshipLocation)).map {
            internshipLocationMapper.mapToDomain(it)
        }
    }

    /**
     * Updates an existing location company.
     */
    override suspend fun updateInternshipLocation(internshipLocation: InternshipLocation): Result<Unit> {
        return remoteDataSource.update(internshipLocation.id, internshipLocationMapper.mapToDto(internshipLocation)).map {
            internshipLocationMapper.mapToDomain(it)
        }
    }

    override suspend fun findRecommendedInternshipLocations(): Result<List<InternshipLocationRecommendedDto>> {
        return try {
            remoteDataSource.getRecommended()
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching location companies: ${e.message}"))
        }
    }

    /**
     * Deletes a location company by its ID.
     */
    override suspend fun deleteInternshipLocation(id: Long): Result<Unit> {
        return remoteDataSource.delete(id)
    }


    override suspend fun findInternshipsByLocationId(locationId: Long): Result<List<Internship>> {
        TODO("Not yet implemented")
    }

    /*
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

    */
}