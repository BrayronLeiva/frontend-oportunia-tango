package oportunia.maps.frontend.taskapp.data.datasource.model


data class RatingCompanyStudentDto(
    var id: Long,
    var rating: Double,
    val type: TypeUser,
    var comment: String
)
