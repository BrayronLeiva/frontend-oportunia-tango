package oportunia.maps.frontend.taskapp.data.mapper

import com.google.android.gms.maps.model.LatLng
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.flat.LocationCompanyFlatDto
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import javax.inject.Inject

/**
 * Mapper class for converting between LocationCompany domain entities and LocationCompanyDto data objects
 */
class LocationCompanyMapper @Inject constructor(private val companyMapper: CompanyMapper) {

    /**
     * Maps a LocationCompanyDto to a domain LocationCompany entity
     * @param dto The data layer location company object to convert
     * @return Domain LocationCompany object
     */
    fun mapToDomain(dto: LocationCompanyDto): LocationCompany = LocationCompany(
        id = dto.id,
        email = dto.email,
        location = LatLng(dto.latitude, dto.longitude),
        contact = dto.contact,
        company = companyMapper.mapToDomain(dto.company)
    )

    /**
     * Maps a domain LocationCompany to a LocationCompanyDto
     * @param domain The domain layer location company object to convert
     * @return LocationCompanyDto object for data layer
     */
    fun mapToDto(domain: LocationCompany): LocationCompanyDto =
        LocationCompanyDto(
            id = domain.id,
            email = domain.email,
            latitude = domain.location.latitude,
            longitude = domain.location.longitude,
            contact = domain.contact,
            company = companyMapper.mapToDto(domain.company)
        )
    fun LocationCompanyFlatDto.toDomain(): LocationCompany {
        return LocationCompany(
            id = id,
            email = email,
            location = LatLng(latitude, longitude),
            contact = contact,
            company = companyMapper.toDomainFromFlat(this)
        )
    }
}