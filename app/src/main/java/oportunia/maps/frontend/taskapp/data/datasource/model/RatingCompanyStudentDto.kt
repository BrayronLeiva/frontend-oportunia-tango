package oportunia.maps.frontend.taskapp.data.datasource.model

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
    val id: Long,
    val rating: Double,
    val type: String,
    val comment: String,
    val student: StudentDto,
    val company: CompanyDto
)
