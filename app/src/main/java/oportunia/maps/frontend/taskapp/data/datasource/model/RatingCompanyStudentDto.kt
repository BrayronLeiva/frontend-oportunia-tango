package oportunia.maps.frontend.taskapp.data.datasource.model

import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser

/**
 * This class represents the Dto of a RatingCompanyStudentDto.
 * @property id The unique identifier of the rating.
 * @property rating The score of the rating.
 * @property type The type of the rating.
 * @property comment The comment of the rating.
 * @property student The student of the rating.
 * @property company The company of the rating.
 */


data class RatingCompanyStudentDto(
    var id: Long,
    var rating: Double,
    var type: TypeUser,
    var comment: String,
    var student: StudentDto,
    var company: CompanyDto
)
