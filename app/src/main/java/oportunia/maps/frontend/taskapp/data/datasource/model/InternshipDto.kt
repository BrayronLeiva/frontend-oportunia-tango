package oportunia.maps.frontend.taskapp.data.datasource.model

/**
 * This class represents the Dto of a Internship.
 * @property id The unique identifier of the certification.
 * @property details The details of the Internship.
 * @property location The location of the Internship.
 */

data class InternshipDto(
    val id: Long,
    val details: String,
    val location: LocationCompanyDto
)
