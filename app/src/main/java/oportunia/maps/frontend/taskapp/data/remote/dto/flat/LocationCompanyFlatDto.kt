package oportunia.maps.frontend.taskapp.data.remote.dto.flat

data class LocationCompanyFlatDto(
    val id: Long,
    val email: String,
    val latitude: Double,
    val longitude: Double,
    val contact: Int,
    val idCompany: Long,
    val name: String,
    val description: String,
    val history: String,
    val mision: String,
    val vision: String,
    val corporateCultur: String,
    val contactCompany: Int,
    val rating: Double,
    val internshipType: String,
    val idUser: Long,
    val emailUser: String,
    val password: String
)
