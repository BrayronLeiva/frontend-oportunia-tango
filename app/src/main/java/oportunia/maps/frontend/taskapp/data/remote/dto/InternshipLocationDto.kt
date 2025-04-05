package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Internship.
 * @property id The unique identifier of the certification.
 * @property location The location of the Internship.
 * @property internship The Internship at the specific location.
 */

data class InternshipLocationDto (
    val id: Long,
    val location: LocationCompanyDto,
    val internship: InternshipDto
)