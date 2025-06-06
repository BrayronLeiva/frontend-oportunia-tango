package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.RequestDto
import oportunia.maps.frontend.taskapp.domain.model.Request
import javax.inject.Inject

/**
 * Mapper class for converting between Request domain entities and RequestDto data objects
 */
class RequestMapper @Inject constructor(
    private val studentMapper: StudentMapper,
    private val internshipLocationMapper: InternshipLocationMapper
) {

    /**
     * Maps a RequestDto to a domain Request entity
     * @param dto The data layer request object to convert
     * @return Domain Request object
     */
    fun mapToDomain(dto: RequestDto): Request = Request(
        id = dto.id,
        student = studentMapper.mapToDomain(dto.studentDto),
        internshipLocation = internshipLocationMapper.mapToDomain(dto.internshipLocationDto),
        state = dto.state
    )

    /**
     * Maps a domain Request to a RequestDto
     * @param domain The domain layer request object to convert
     * @return RequestDto object for data layer
     */
    fun mapToDto(domain: Request): RequestDto =
        RequestDto(
            id = domain.id,
            studentDto = studentMapper.mapToDto(domain.student),
            internshipLocationDto = internshipLocationMapper.mapToDto(domain.internshipLocation),
            state = domain.state
        )
}