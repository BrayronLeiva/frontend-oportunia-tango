package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents student a Certification.
 * @property id The unique identifier of the certification.
 * @property name The label of the certification.
 * @property provider The provider of the certification
 * @property student The student related to the certification
 */

data class Certification(
    val id: Long,
    val name: String,
    val provider: String,
    val student: Student
)
