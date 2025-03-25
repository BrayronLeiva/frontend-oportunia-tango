package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.QuestionDto
import oportunia.maps.frontend.taskapp.domain.model.Question

/**
 * Mapper class for converting between Question domain entities and QuestionDto data objects
 */
class QuestionMapper(private val companyMapper: CompanyMapper) {

    /**
     * Maps a QuestionDto to a domain Question entity
     * @param dto The data layer question object to convert
     * @return Domain Question object
     */
    fun mapToDomain(dto: QuestionDto): Question = Question(
        id = dto.id,
        question = dto.question,
        answer = dto.answer,
        company = companyMapper.mapToDomain(dto.company)
    )

    /**
     * Maps a domain Question to a QuestionDto
     * @param domain The domain layer question object to convert
     * @return QuestionDto object for data layer
     */
    fun mapToDto(domain: Question): QuestionDto = QuestionDto(
        id = domain.id,
        question = domain.question,
        answer = domain.answer,
        company = companyMapper.mapToDto(domain.company)
    )
}