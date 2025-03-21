package oportunia.maps.frontend.taskapp.data.datasource.model

/**
 * This class represents the Dto of a LocationCompany.
 * @property id The unique identifier of the location of the company.
 * @property email The email of the location of the company.
 * @property contact The contact of the location of the company.
 * @property company The company of the location of the company.
 */

data class LocationCompanyDto(
    val id: Long,
    val email: String,
    val contact: Int,
    val company: CompanyDto

)
