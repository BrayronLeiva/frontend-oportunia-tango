package oportunia.maps.frontend.taskapp.domain.repository

import oportunia.maps.frontend.taskapp.domain.model.LocationCompany

/**
 * This interface represents the LocationCompanyRepository.
 */
interface LocationCompanyRepository {
    suspend fun findAllLocations(): Result<List<LocationCompany>>
    suspend fun findLocationById(id: Long): Result<LocationCompany>
    suspend fun saveLocation(location: LocationCompany): Result<Unit>
    suspend fun deleteLocation(id: Long): Result<Unit>
    suspend fun updateLocation(location: LocationCompany): Result<Unit>
}