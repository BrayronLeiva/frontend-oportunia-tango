package oportunia.maps.frontend.taskapp.data.datasource.locationcompany

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.mapper.LocationCompanyMapper

/**
 * LocationCompanyDataSource implementation.
 * @param locationCompanyMapper The mapper for converting between data and domain layer location company objects.
 */
class LocationCompanyDataSourceImpl(
    private val locationCompanyMapper: LocationCompanyMapper
) : LocationCompanyDataSource {

    override suspend fun getLocationsCompany(): Flow<List<LocationCompanyDto>> = flow {
        val locationCompanies = LocationCompanyProvider.findAllLocationCompanies()
        emit(locationCompanies.map { locationCompanyMapper.mapToDto(it) })
    }

    override suspend fun getLocationCompanyById(locationId: Long): LocationCompanyDto? =
        LocationCompanyProvider.findLocationCompanyById(locationId)?.let { locationCompanyMapper.mapToDto(it) }

    override suspend fun insertLocationCompany(locationCompanyDto: LocationCompanyDto) {
        val locationCompany = locationCompanyMapper.mapToDomain(locationCompanyDto)
        LocationCompanyProvider.addLocationCompany(locationCompany)
    }

    override suspend fun updateLocationCompany(locationCompanyDto: LocationCompanyDto) {
        val locationCompany = locationCompanyMapper.mapToDomain(locationCompanyDto)
        LocationCompanyProvider.updateLocationCompany(locationCompany)
    }

    override suspend fun deleteLocationCompany(locationCompanyDto: LocationCompanyDto) {
        val locationCompany = locationCompanyMapper.mapToDomain(locationCompanyDto)
        LocationCompanyProvider.deleteLocationCompany(locationCompany)
    }
}