package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.LocationCompanyDto
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany

/**
 * Mapper class for converting between LocationCompany domain entities and LocationCompanyDto data objects
 */
class LocationCompanyMapper(private val companyMapper: CompanyMapper) {

    /**
     * Maps a LocationCompanyDto to a domain LocationCompany entity
     * @param dto The data layer location company object to convert
     * @return Domain LocationCompany object
     */
    fun mapToDomain(dto: LocationCompanyDto): LocationCompany = LocationCompany(
        id = dto.id,
        email = dto.email,
        contact = dto.contact,
        company = companyMapper.mapToDomain(dto.company)
    )

    /**
     * Maps a domain LocationCompany to a LocationCompanyDto
     * @param domain The domain layer location company object to convert
     * @return LocationCompanyDto object for data layer
     */
    fun mapToDto(domain: LocationCompany): LocationCompanyDto = LocationCompanyDto(
        id = domain.id,
        email = domain.email,
        contact = domain.contact,
        company = companyMapper.mapToDto(domain.company)
    )
}