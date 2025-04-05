package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.remote.dto.RequestDto
import oportunia.maps.frontend.taskapp.domain.model.Request

/**
 * Mapper class for converting between Request domain entities and RequestDto data objects
 */
class RequestMapper(
    private val studentMapper: StudentMapper,
    private val companyMapper: CompanyMapper
) {

    /**
     * Maps a RequestDto to a domain Request entity
     * @param dto The data layer request object to convert
     * @return Domain Request object
     */
    fun mapToDomain(dto: RequestDto): Request = Request(
        id = dto.id,
        student = studentMapper.mapToDomain(dto.student),
        company = companyMapper.mapToDomain(dto.company),
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
            student = studentMapper.mapToDto(domain.student),
            company = companyMapper.mapToDto(domain.company),
            state = domain.state
        )
}