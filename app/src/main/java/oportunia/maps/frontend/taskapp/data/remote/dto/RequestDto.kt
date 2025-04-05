package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Request.
 * @property id The unique identifier of the request
 * @property student The contact of the request
 * @property company The company of the request
 * @property state The state of the request
 */

data class RequestDto(
    val id: Long,
    val student: StudentDto,
    val company: CompanyDto,
    val state: Boolean,

    )
