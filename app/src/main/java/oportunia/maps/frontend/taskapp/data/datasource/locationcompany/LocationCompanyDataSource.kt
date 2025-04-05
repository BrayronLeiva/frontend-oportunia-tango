package oportunia.maps.frontend.taskapp.data.datasource.locationcompany

import kotlinx.coroutines.flow.Flow
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto

/**
 * This interface represents the LocationCompanyRepository.
 */
interface LocationCompanyDataSource {
    suspend fun getLocationsCompany(): Flow<List<LocationCompanyDto>>
    suspend fun getLocationCompanyById(locationId: Long): LocationCompanyDto?
    suspend fun insertLocationCompany(locationCompanyDto: LocationCompanyDto)
    suspend fun deleteLocationCompany(locationCompanyDto: LocationCompanyDto)
    suspend fun updateLocationCompany(locationCompanyDto: LocationCompanyDto)
}