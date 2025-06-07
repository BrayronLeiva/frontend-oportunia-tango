package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents the internship request done by a student.
 * @property id The unique identifier of the request
 * @property student The contact of the request
 * @property company The company of the request
 * @property state The state of the request
 */

data class Request(
    val id: Long,
    val student: Student,
    val internshipLocation: InternshipLocation,
    val state: Boolean,
    )
