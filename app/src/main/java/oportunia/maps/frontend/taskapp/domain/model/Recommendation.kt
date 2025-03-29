package oportunia.maps.frontend.taskapp.domain.model

/**
 * This class represents the student recommendation done by a company.
 * @property id The unique identifier of the recommendation
 * @property details The email of the recommendation
 * @property student The contact of the recommendation
 * @property company The company of the recommendation
 */

data class Recommendation(
    val id: Long,
    val details: String,
    val student: Student,
    val company: Company
)
