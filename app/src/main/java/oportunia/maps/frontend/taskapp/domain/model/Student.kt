package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents a student.
 * @property id The unique identifier of the student.
 * @property name The name of the student.
 * @property identification The identification of the student.
 * @property personalInfo The personal information of the student.
 * @property experience The experience of the student.
 * @property rating The rating of the student.
 * @property user The user of the student.
 */



data class Student(
    val id: Long,
    val name: String,
    val identification: String,
    val personalInfo: String,
    val experience: String,
    val rating: Double,
    val user: User

)
