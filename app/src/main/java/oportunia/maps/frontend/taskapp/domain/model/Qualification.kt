package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents the qualification of a student.
 * @property id The unique identifier of the qualification of the student.
 * @property name The name of the qualification of the student.
 * @property area The area of the qualification of the student.
 * @property student The student of the qualification of the student.
 */

data class Qualification(
    val id: Long,
    val name: String,
    val area: String,
    val student: Student?
)
