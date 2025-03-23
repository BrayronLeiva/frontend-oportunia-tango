package oportunia.maps.frontend.taskapp.data.datasource.model

/**
 * This class represents the Dto of a Company.
 * @property id The unique identifier of the company.
 * @property name The label of the company.
 * @property description The description of the company
 * @property history The history of the company
 * @property mision The mision of the company
 * @property vision The vision of the company
 * @property corporateCultur The corporate Culture of the company
 * @property contact The contact of the company
 * @property rating The rating of the company
 * @property internshipType The internship type offer by the company
 * @property user The user related to the company
 */

data class CompanyDto(
    val id: Long,
    val name: String,
    val description: String,
    val history: String,
    val mision: String,
    val vision: String,
    val corporateCultur: String,
    val contact: Int,
    val rating: Double,
    val internshipType: String,
    val user: UserDto
)
