package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a Recommendation.
 * @property id The unique identifier of the recommendation
 * @property details The email of the recommendation
 * @property student The contact of the recommendation
 * @property company The company of the recommendation
 */

data class RecommendationDto(
    val id: Long,
    val details: String,
    val student: StudentDto,
    val company: CompanyDto
)
