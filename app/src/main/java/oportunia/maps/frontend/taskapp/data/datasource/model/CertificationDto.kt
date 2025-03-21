package oportunia.maps.frontend.taskapp.data.datasource.model

import java.util.Date

/**
 * This class represents the Dto of a Certification.
 * @property id The unique identifier of the certification.
 * @property name The label of the certification.
 * @property provider The provider of the certification
 * @property student The student related to the certification
 */

data class CertificationDto(
    val id: Long,
    val name: String,
    val provider: String,
    val student: StudentDto
)
