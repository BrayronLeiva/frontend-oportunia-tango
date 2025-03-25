package oportunia.maps.frontend.taskapp.domain.model

import com.google.android.gms.maps.model.LatLng

/**
 * This class represents the location of a company.
 * @property id The unique identifier of the location of the company.
 * @property email The email of the location of the company.
 * @property location The location of the company.
 * @property contact The contact of the location of the company.
 * @property company The company of the location of the company.
 */

data class LocationCompany(
    val id: Long,
    val email: String,
    val location: LatLng,
    val contact: Int,
    val company: Company

)
