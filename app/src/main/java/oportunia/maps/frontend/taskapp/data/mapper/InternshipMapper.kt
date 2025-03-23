package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.InternshipDto
import oportunia.maps.frontend.taskapp.domain.model.Internship

/**
 * Mapper class for converting between Internship domain entities and InternshipDto data objects
 */
class InternshipMapper(private val locationCompanyMapper: LocationCompanyMapper) {

    /**
     * Maps an InternshipDto to a domain Internship entity
     * @param dto The data layer internship object to convert
     * @return Domain Internship object
     */
    fun mapToDomain(dto: InternshipDto): Internship = Internship(
        id = dto.id,
        details = dto.details,
        location = locationCompanyMapper.mapToDomain(dto.location)
    )

    /**
     * Maps a domain Internship to an InternshipDto
     * @param domain The domain layer internship object to convert
     * @return InternshipDto object for data layer
     */
    fun mapToDto(domain: Internship): InternshipDto = InternshipDto(
        id = domain.id,
        details = domain.details,
        location = locationCompanyMapper.mapToDto(domain.location)
    )
}
