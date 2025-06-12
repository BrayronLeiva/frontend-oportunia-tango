package oportunia.maps.frontend.taskapp.data.remote.serializer



import android.util.Log
import com.google.gson.*
import oportunia.maps.frontend.taskapp.data.remote.dto.*
import java.lang.reflect.Type

class RequestDeserializer : JsonDeserializer<RequestDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RequestDto {
        val jsonObject = json.asJsonObject

        val idRequest = jsonObject.get("idRequest").asLong
        val state = jsonObject.get("state").asBoolean

        // Deserialize Student
        val studentJson = jsonObject.getAsJsonObject("student")
        val idStudent = studentJson.get("idStudent").asLong
        val nameStudent = studentJson.get("nameStudent").asString
        val identification = studentJson.get("identification").asString
        val personalInfo = studentJson.get("personalInfo").asString
        val experience = studentJson.get("experience").asString
        val ratingStudent = studentJson.get("ratingStudent").asDouble
        val imageProfile = try {
            jsonObject.get("imageProfile").asString ?: "empty"
        } catch (e: Exception) {
            Log.e("StudentDeserializer", "Error al obtener 'tokenExpired': ${e.message}")
            "empty"
        }
        val homeLatitude = studentJson.get("homeLatitude").asDouble
        val homeLongitude = studentJson.get("homeLongitude").asDouble

        // Deserialize User inside Student
        val userJson = studentJson.getAsJsonObject("user")
        val userDto = context.deserialize<UserDto>(userJson, UserDto::class.java)

        val studentDto = StudentDto(
            idStudent,
            nameStudent,
            identification,
            personalInfo,
            experience,
            ratingStudent,
            homeLatitude,
            homeLongitude,
            imageProfile,
            userDto
        )

        // Deserialize InternshipLocation
        val internshipLocationJson = jsonObject.getAsJsonObject("internshipLocation")
        val idInternshipLocation = internshipLocationJson.get("idInternshipLocation").asLong

        // Deserialize LocationCompany
        val locationCompanyJson = internshipLocationJson.getAsJsonObject("locationCompany")
        val idLocationCompany = locationCompanyJson.get("idLocationCompany").asLong
        val latitude = locationCompanyJson.get("latitude").asDouble
        val longitude = locationCompanyJson.get("longitude").asDouble
        val email = locationCompanyJson.get("email").asString
        val contactLocation = locationCompanyJson.get("contactLocation").asInt

        // Deserialize Company
        val companyJson = locationCompanyJson.getAsJsonObject("company")
        val idCompany = companyJson.get("idCompany").asLong
        val nameCompany = companyJson.get("nameCompany").asString
        val description = companyJson.get("description").asString
        val history = companyJson.get("history").asString
        val mision = companyJson.get("mision").asString
        val vision = companyJson.get("vision").asString
        val corporateCultur = companyJson.get("corporateCultur").asString
        val contactCompany = companyJson.get("contactCompany").asInt
        val ratingCompany = companyJson.get("ratingCompany").asDouble
        val companyImageProfile = try {
            companyJson.get("imageProfile")?.asString ?: "empty"
        } catch (e: Exception) {
            Log.e("RequestDeserializer", "Error al obtener 'imageProfile': ${e.message}")
            "empty"
        }


        var internshipType = companyJson.get("internshipType").asString
        if (internshipType !in listOf("REM", "INP", "MIX")) {
            internshipType = "REM"
        }

        // Deserialize User in Company
        val userCompanyJson = companyJson.getAsJsonObject("user")
        val userCompanyDto = context.deserialize<UserDto>(userCompanyJson, UserDto::class.java)

        val companyDto = CompanyDto(
            idCompany,
            nameCompany,
            description,
            history,
            mision,
            vision,
            corporateCultur,
            contactCompany,
            ratingCompany,
            internshipType,
            companyImageProfile,
            userCompanyDto
        )

        val locationCompanyDto = LocationCompanyDto(
            idLocationCompany,
            email,
            latitude,
            longitude,
            contactLocation,
            companyDto
        )

        // Deserialize Internship
        val internshipJson = internshipLocationJson.getAsJsonObject("internship")
        val idInternship = internshipJson.get("idInternship").asLong
        val details = internshipJson.get("details").asString
        val internshipDto = InternshipDto(idInternship, details)

        val internshipLocationDto = InternshipLocationDto(
            idInternshipLocation,
            locationCompanyDto,
            internshipDto
        )

        return RequestDto(idRequest, studentDto, internshipLocationDto, state)
    }
}
