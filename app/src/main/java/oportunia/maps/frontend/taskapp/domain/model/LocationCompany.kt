package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents the location of a company.
 * @property id The unique identifier of the location of the company.
 * @property email The email of the location of the company.
 * @property contact The contact of the location of the company.
 * @property company The company of the location of the company.
 */

data class LocationCompany(
    val id: Long,
    val email: String,
    val contact: Int,
    val company: Company

)
