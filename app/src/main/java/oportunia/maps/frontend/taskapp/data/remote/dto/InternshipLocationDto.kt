package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Internship.
 * @property id The unique identifier of the certification.
 * @property locationCompany The location of the Internship.
 * @property internship The Internship at the specific location.
 */

data class InternshipLocationDto (
    val idInternshipLocation: Long,
    val locationCompany: LocationCompanyDto,
    val internship: InternshipDto
)