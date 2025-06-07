package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Internship.
 * @property id The unique identifier of the certification.
 * @property details The details of the Internship.
 */

data class InternshipDto(
    val id: Long? = null,
    val details: String,
)
