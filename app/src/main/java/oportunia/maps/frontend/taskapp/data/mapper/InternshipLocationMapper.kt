package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import javax.inject.Inject

/**
 * Mapper class for converting between InternshipLocation domain entities and InternshipLocationDto data objects
 */
class InternshipLocationMapper @Inject constructor(
    private val locationCompanyMapper: LocationCompanyMapper,
    private val internshipMapper: InternshipMapper
) {

    /**
     * Maps an InternshipLocationDto to a domain InternshipLocation entity
     * @param dto The data layer internship location object to convert
     * @return Domain InternshipLocation object
     */
    fun mapToDomain(dto: InternshipLocationDto): InternshipLocation = InternshipLocation(
        id = dto.idInternshipLocation,
        location = locationCompanyMapper.mapToDomain(dto.locationCompany),
        internship = internshipMapper.mapToDomain(dto.internship)
    )

    /**
     * Maps a domain InternshipLocation to an InternshipLocationDto
     * @param domain The domain layer internship location object to convert
     * @return InternshipLocationDto object for data layer
     */
    fun mapToDto(domain: InternshipLocation): InternshipLocationDto =
        InternshipLocationDto(
            idInternshipLocation = domain.id,
            locationCompany = locationCompanyMapper.mapToDto(domain.location),
            internship = internshipMapper.mapToDto(domain.internship)
        )
}