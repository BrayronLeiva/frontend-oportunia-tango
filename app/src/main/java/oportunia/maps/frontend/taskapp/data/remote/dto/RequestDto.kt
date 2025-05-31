package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Request.
 * @property id The unique identifier of the request
 * @property studentDto The contact of the request
 * @property company The company of the request
 * @property state The state of the request
 */

data class RequestDto(
    val id: Long,
    val studentDto: StudentDto,
    val internshipLocationDto: InternshipLocationDto,
    val state: Boolean,
    )
