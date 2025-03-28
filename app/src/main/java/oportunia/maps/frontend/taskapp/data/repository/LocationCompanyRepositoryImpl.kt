package oportunia.maps.frontend.taskapp.data.repository

import oportunia.maps.frontend.taskapp.data.datasource.locationcompany.LocationCompanyDataSource
import oportunia.maps.frontend.taskapp.data.mapper.LocationCompanyMapper
import oportunia.maps.frontend.taskapp.domain.error.DomainError
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

/**
 * Implementation of [LocationCompanyRepository] that handles location company data operations.
 * Provides error handling and mapping between data and domain layers.
 *
 * @property dataSource The data source for location company operations.
 * @property locationCompanyMapper The mapper for converting between domain and data layer location company objects.
 */
class LocationCompanyRepositoryImpl(
    private val dataSource: LocationCompanyDataSource,
    private val locationCompanyMapper: LocationCompanyMapper
) : LocationCompanyRepository {

    override suspend fun findAllLocations(): Result<List<LocationCompany>> = runCatching {
        dataSource.getLocationsCompany().first().map { locationDto ->
            locationCompanyMapper.mapToDomain(locationDto)
        }
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch locations")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping locations")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findLocationById(id: Long): Result<LocationCompany> = runCatching {
        val locationDto =
            dataSource.getLocationCompanyById(id) ?: throw DomainError.TaskError("Location not found")
        locationCompanyMapper.mapToDomain(locationDto)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping location")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun saveLocation(location: LocationCompany): Result<Unit> = runCatching {
        dataSource.insertLocationCompany(locationCompanyMapper.mapToDto(location))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to save location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping location")
            else -> throw DomainError.TaskError("Failed to save location: ${throwable.message}")
        }
    }

    override suspend fun deleteLocation(id: Long): Result<Unit> = runCatching {
        val location = dataSource.getLocationCompanyById(id) ?: throw DomainError.TaskError("Location not found")
        dataSource.deleteLocationCompany(location)
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to delete location")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun updateLocation(location: LocationCompany): Result<Unit> = runCatching {
        dataSource.updateLocationCompany(locationCompanyMapper.mapToDto(location))
    }.recoverCatching { throwable ->
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to update location")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping location")
            else -> throw DomainError.TaskError("Failed to update location: ${throwable.message}")
        }
    }
}