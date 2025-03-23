package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.CertificationDto
import oportunia.maps.frontend.taskapp.domain.model.Certification

/**
 * Mapper class for converting between Certification domain entities and CertificationDto data objects
 */
class CertificationMapper(private val studentMapper: StudentMapper) {

    /**
     * Maps a CertificationDto to a domain Certification entity
     * @param dto The data layer certification object to convert
     * @return Domain Certification object
     */
    fun mapToDomain(dto: CertificationDto): Certification = Certification(
        id = dto.id,
        name = dto.name,
        provider = dto.provider,
        student = studentMapper.mapToDomain(dto.student)
    )

    /**
     * Maps a domain Certification to a CertificationDto
     * @param domain The domain layer certification object to convert
     * @return CertificationDto object for data layer
     */
    fun mapToDto(domain: Certification): CertificationDto = CertificationDto(
        id = domain.id,
        name = domain.name,
        provider = domain.provider,
        student = studentMapper.mapToDto(domain.student)
    )
}