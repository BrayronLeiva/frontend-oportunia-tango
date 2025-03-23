package oportunia.maps.frontend.taskapp.data.mapper

import oportunia.maps.frontend.taskapp.data.datasource.model.RatingCompanyStudentDto
import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.domain.model.RatingCompanyStudent

/**
 * Mapper class for converting between RatingCompanyStudent domain entities and RatingCompanyStudentDto data objects
 */
class RatingCompanyStudentMapper(
    private val studentMapper: StudentMapper,
    private val companyMapper: CompanyMapper
) {

    /**
     * Maps a RatingCompanyStudentDto to a domain RatingCompanyStudent entity
     * @param dto The data layer rating object to convert
     * @return Domain RatingCompanyStudent object
     */
    fun mapToDomain(dto: RatingCompanyStudentDto): RatingCompanyStudent = RatingCompanyStudent(
        id = dto.id,
        rating = dto.rating,
        type = TypeUser.valueOf(dto.type),
        comment = dto.comment,
        student = studentMapper.mapToDomain(dto.student),
        company = companyMapper.mapToDomain(dto.company)
    )

    /**
     * Maps a domain RatingCompanyStudent to a RatingCompanyStudentDto
     * @param domain The domain layer rating object to convert
     * @return RatingCompanyStudentDto object for data layer
     */
    fun mapToDto(domain: RatingCompanyStudent): RatingCompanyStudentDto = RatingCompanyStudentDto(
        id = domain.id,
        rating = domain.rating,
        type = domain.type.name,
        comment = domain.comment,
        student = studentMapper.mapToDto(domain.student),
        company = companyMapper.mapToDto(domain.company)
    )
}