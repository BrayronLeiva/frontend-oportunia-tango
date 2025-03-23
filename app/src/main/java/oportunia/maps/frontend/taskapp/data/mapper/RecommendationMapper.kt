package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.RecommendationDto
import oportunia.maps.frontend.taskapp.domain.model.Recommendation

/**
 * Mapper class for converting between Recommendation domain entities and RecommendationDto data objects
 */
class RecommendationMapper(
    private val studentMapper: StudentMapper,
    private val companyMapper: CompanyMapper
) {

    /**
     * Maps a RecommendationDto to a domain Recommendation entity
     * @param dto The data layer recommendation object to convert
     * @return Domain Recommendation object
     */
    fun mapToDomain(dto: RecommendationDto): Recommendation = Recommendation(
        id = dto.id,
        details = dto.details,
        student = studentMapper.mapToDomain(dto.student),
        company = companyMapper.mapToDomain(dto.company)
    )

    /**
     * Maps a domain Recommendation to a RecommendationDto
     * @param domain The domain layer recommendation object to convert
     * @return RecommendationDto object for data layer
     */
    fun mapToDto(domain: Recommendation): RecommendationDto = RecommendationDto(
        id = domain.id,
        details = domain.details,
        student = studentMapper.mapToDto(domain.student),
        company = companyMapper.mapToDto(domain.company)
    )
}