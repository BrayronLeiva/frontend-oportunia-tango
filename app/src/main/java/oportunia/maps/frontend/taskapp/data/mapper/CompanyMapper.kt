package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.InternshipType
import oportunia.maps.frontend.taskapp.data.datasource.model.CompanyDto
import oportunia.maps.frontend.taskapp.domain.model.Company

/**
 * Mapper class for converting between Company domain entities and CompanyDto data objects
 */
class CompanyMapper(
    private val userMapper: UserMapper
) {

    /**
     * Maps a CompanyDto to a domain Company entity
     * @param dto The data layer company object to convert
     * @return Domain Company object
     */
    fun mapToDomain(dto: CompanyDto): Company = Company(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        history = dto.history,
        mision = dto.mision,
        vision = dto.vision,
        corporateCultur = dto.corporateCultur,
        contact = dto.contact,
        rating = dto.rating,
        internshipType = InternshipType.valueOf(dto.internshipType),  // Converts String to Enum
        user = userMapper.mapToDomain(dto.user)
    )

    /**
     * Maps a domain Company to a CompanyDto
     * @param domain The domain layer company object to convert
     * @return CompanyDto object for data layer
     */
    fun mapToDto(domain: Company): CompanyDto = CompanyDto(
        id = domain.id,
        name = domain.name,
        description = domain.description,
        history = domain.history,
        mision = domain.mision,
        vision = domain.vision,
        corporateCultur = domain.corporateCultur,
        contact = domain.contact,
        rating = domain.rating,
        internshipType = domain.internshipType.name,  // Converts Enum to String
        user = userMapper.mapToDto(domain.user)
    )
}