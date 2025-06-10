package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Student.
 * @property id The unique identifier of the student.
 * @property name The name of the student.
 * @property identification The identification of the student.
 * @property personalInfo The personal information of the student.
 * @property experience The experience of the student.
 * @property rating The rating of the student.
 * @property user The user of the student.
 */



data class StudentDto(
    val id: Long,
    val name: String,
    val identification: String,
    val personalInfo: String,
    val experience: String,
    val rating: Double,
    val user: UserDto

)

data class StudentRecommendedDto(
    val id: Long,
    val name: String,
    val identification: String,
    val personalInfo: String,
    val experience: String,
    val rating: Double,
    val user: UserDto,
    val score: Int,
    val reason: String

)


data class StudentCreateDto(
    val nameStudent: String,
    val identification: String,
    val personalInfo: String,
    val experience: String,
    val ratingStudent: Double,
    val userId: Long

)