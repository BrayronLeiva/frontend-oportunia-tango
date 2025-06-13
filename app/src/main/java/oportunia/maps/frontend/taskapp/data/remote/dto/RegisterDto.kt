package oportunia.maps.frontend.taskapp.data.remote.dto

data class RegisterStudentCreateDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val enabled: Boolean,
    val tokenExpired: Boolean,

    val nameStudent: String,
    val identification: String,
    val personalInfo: String,
    val experience: String,
    val ratingStudent: Double,
    val userId: Long,
    val homeLatitude: Double,
    val homeLongitude: Double,
    val imageProfile: String
)

data class RegisterCompanyCreateDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val enabled: Boolean,
    val tokenExpired: Boolean,

    val nameCompany: String,
    val description: String,
    val history: String,
    val mision: String,
    val vision: String,
    val corporateCultur: String,
    val contactCompany: Int,
    val ratingCompany: Double,
    val internshipType: String,
    val imageProfile: String
)

data class RegisterStudentDto(
    val user: UserDto,
    val student: StudentDto
)

data class RegisterCompanyDto(
    val user: UserDto,
    val company: CompanyDto
)