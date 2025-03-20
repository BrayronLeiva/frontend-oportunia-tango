package oportunia.maps.frontend.taskapp.data.datasource.model

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
)
