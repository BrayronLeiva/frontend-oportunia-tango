package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents an internship in a location.
 * @property id The unique identifier of the certification.
 * @property details The details of the Internship.
 * @property location The location of the Internship.
 */

data class Internship(
    var id: Long,
    var details: String,
    val location: LocationCompany
)
