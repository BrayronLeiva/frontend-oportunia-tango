package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.InternshipLocationMapper
import oportunia.maps.frontend.taskapp.data.mapper.InternshipMapper
import oportunia.maps.frontend.taskapp.data.remote.InternshipLocationRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.InternshipRemoteDataSource
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import oportunia.maps.frontend.taskapp.domain.repository.InternshipRepository
import java.net.UnknownHostException
import javax.inject.Inject

class InternshipRepositoryImpl  @Inject constructor(
    private val remoteDataSource: InternshipRemoteDataSource,
    private val internshipMapper: InternshipMapper
) : InternshipRepository {


    override suspend fun findAllInternships(): Result<List<Internship>> {
        return try {
            remoteDataSource.getAll().map { dtos ->
                dtos.map { internshipMapper.mapToDomain(it) }
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
    override suspend fun findInternshipById(id: Long): Result<Internship> {
        return remoteDataSource.getById(id).map {
            internshipMapper.mapToDomain(it)
        }
    }


    /**
     * Creates a new location company.
     */
    override suspend fun saveInternship(internship: Internship): Result<Internship> {
        val dto = internshipMapper.mapToDto(internship)
        return remoteDataSource.create(dto)
            .map { savedDto -> internshipMapper.mapToDomain(savedDto) }
    }

    /**
     * Updates an existing location company.
     */
    override suspend fun updateInternship(internship: Internship): Result<Unit> {
        return remoteDataSource.update(internship.id!!, internshipMapper.mapToDto(internship)).map {
            internshipMapper.mapToDomain(it)
        }
    }

    /**
     * Deletes a location company by its ID.
     */
    override suspend fun deleteInternship(id: Long): Result<Unit> {
        return remoteDataSource.delete(id)
    }

    override suspend fun findInternshipsByLocationId(locationId: Long): Result<List<Internship>>{
        return try {
            remoteDataSource.getInternshipsByLocationId(locationId).map { dtos ->
                dtos.map { internshipMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching location companies: ${e.message}"))
        }
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