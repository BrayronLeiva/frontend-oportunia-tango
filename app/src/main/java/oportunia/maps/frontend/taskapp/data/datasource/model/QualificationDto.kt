package oportunia.maps.frontend.taskapp.data.datasource.model

/**
 * This class represents the Dto of a Qualification.
 * @property id The unique identifier of the qualification of the student.
 * @property name The name of the qualification of the student.
 * @property area The area of the qualification of the student.
 * @property student The student of the qualification of the student.
 */

data class QualificationDto(
    val id: Long,
    val name: String,
    val area: String,
    val student: StudentDto?
)
