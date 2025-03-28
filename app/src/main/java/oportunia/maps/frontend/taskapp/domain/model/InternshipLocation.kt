package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents the InternshipLocation of a company.
 * @property id The unique identifier of the InternshipLocation.
 * @property location The location of the InternshipLocation.
 * @property internship The Internship at the specific location.
 */

data class InternshipLocation (
    val id: Long,
    val location: LocationCompany,
    val internship: Internship
)