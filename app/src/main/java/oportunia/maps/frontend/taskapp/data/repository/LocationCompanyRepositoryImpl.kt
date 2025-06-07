package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.mapper.LocationCompanyMapper
import oportunia.maps.frontend.taskapp.data.remote.LocationCompanyRemoteDataSource
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Implementation of [LocationCompanyRepository] that handles location company data operations.
 *
 * @property remoteDataSource Remote data source for LocationCompany
 * @property mapper Mapper to convert between DTO and domain models
 */
class LocationCompanyRepositoryImpl @Inject constructor(
    private val remoteDataSource: LocationCompanyRemoteDataSource,
    private val mapper: LocationCompanyMapper
) : LocationCompanyRepository {

    /**
     * Retrieves all location companies.
     */
    override suspend fun findAllLocations(): Result<List<LocationCompany>> {
        return try {
            remoteDataSource.getAll().map { dtos ->
                dtos.map { mapper.mapToDomain(it) }
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
    override suspend fun findLocationById(id: Long): Result<LocationCompany> {
        return remoteDataSource.getById(id).map {
            mapper.mapToDomain(it)
        }
    }

    /**
     * Creates a new location company.
     */
    override suspend fun saveLocation(location: LocationCompany): Result<Unit> {
        return remoteDataSource.create(mapper.mapToRequestDto(location)).map {
            mapper.mapToDomain(it)
        }
    }

    /**
     * Updates an existing location company.
     */
    override suspend fun updateLocation(location: LocationCompany): Result<Unit> {
        return remoteDataSource.update(location.id!!, mapper.mapToDto(location)).map {
            mapper.mapToDomain(it)
        }
    }

    /**
     * Deletes a location company by its ID.
     */
    override suspend fun deleteLocation(id: Long): Result<Unit> {
        return remoteDataSource.delete(id)
    }
}