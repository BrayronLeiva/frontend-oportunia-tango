package oportunia.maps.frontend.taskapp.data.remote.dto

/**
 * This class represents the Dto of a LocationCompany.
 * @property id The unique identifier of the location of the company.
 * @property email The email of the location of the company.
 * @property contact The contact of the location of the company.
 * @property company The company of the location of the company.
 */

data class LocationCompanyDto(
    val id: Long? = null,
    val email: String,
    val latitude: Double,
    val longitude: Double,
    val contact: Int,
    val company: CompanyDto
)

data class LocationCompanyRequestDto(
    val latitude: Double,
    val longitude: Double,
    val email: String,
    val contactLocation: Int,
    val companyId: Long
)
