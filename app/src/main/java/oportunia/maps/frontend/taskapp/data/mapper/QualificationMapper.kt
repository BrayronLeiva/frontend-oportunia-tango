package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.domain.model.Qualification

/**
 * Mapper class for converting between Qualification domain entities and QualificationDto data objects
 */
class QualificationMapper(private val studentMapper: StudentMapper?) {

    /**
     * Maps a QualificationDto to a domain Qualification entity
     * @param dto The data layer qualification object to convert
     * @return Domain Qualification object
     */
    fun mapToDomain(dto: QualificationDto): Qualification = Qualification(
        id = dto.id,
        name = dto.name,
        area = dto.area,
        student = null
    )

    /**
     * Maps a domain Qualification to a QualificationDto
     * @param domain The domain layer qualification object to convert
     * @return QualificationDto object for data layer
     */
    fun mapToDto(domain: Qualification): QualificationDto =
        QualificationDto(
            id = domain.id,
            name = domain.name,
            area = domain.area,
            student = null
        )
}