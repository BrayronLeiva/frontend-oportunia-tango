package oportunia.maps.frontend.taskapp.data.remote.serializer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import java.lang.reflect.Type


class InternshipLocationRecommendedSerializer : JsonDeserializer<InternshipLocationRecommendedDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): InternshipLocationRecommendedDto {
        val jsonObject = json.asJsonObject

        // Deserialize InternshipLocation fields
        val idInternshipLocation = jsonObject.get("idInternshipLocation").asLong
        val score = jsonObject.get("score").asDouble
        val reason = jsonObject.get("reason").asString

        // Access locationCompany object
        val locationCompanyJson = jsonObject.getAsJsonObject("locationCompany")
        val idLocation = locationCompanyJson.get("idLocationCompany").asLong
        val latitude = locationCompanyJson.get("latitude").asDouble
        val longitude = locationCompanyJson.get("longitude").asDouble
        val email = locationCompanyJson.get("email").asString
        val contactLocation = locationCompanyJson.get("contactLocation").asInt

        // Access company object
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
        val imageProfile = try {
            companyJson.get("imageProfile")?.asString ?: "empty"
        } catch (e: Exception) {
            Log.e("InternshipLocationRecommendedSerializer", "Error al obtener 'imageProfile': ${e.message}")
            "empty"
        }

        var internshipType = companyJson.get("internshipType").asString
        if (internshipType != "REM" && internshipType != "INP" && internshipType != "MIX") {
            internshipType = "REM"
        }

        // Access user object
        val userJson = companyJson.getAsJsonObject("user")
        val userId = userJson.get("id").asLong
        val firstName = userJson.get("firstName").asString
        val lastName = userJson.get("lastName").asString
        val emailUser = userJson.get("email").asString
        val enabled = userJson.get("enabled").asBoolean
        val tokenExpired = userJson.get("tokenExpired").asBoolean
        val createDate = userJson.get("createDate").asString


        val userDto = UserDto(
            id = userId,
            email = emailUser,
            firstName = firstName,
            lastName = lastName,
            enable = enabled,
            tokenExpired = tokenExpired,
            createDate = createDate,
            roleList = emptyList()
        )

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
            imageProfile,
            userDto
        )

        val locationCompanyDto = LocationCompanyDto(
            idLocation,
            email,
            latitude,
            longitude,
            contactLocation,
            companyDto
        )

        // Deserialize internship
        val internshipJson = jsonObject.getAsJsonObject("internship")
        val idInternship = internshipJson.get("idInternship").asLong
        val details = internshipJson.get("details").asString
        val internshipDto = InternshipDto(idInternship, details)

        // Compose final object
        return InternshipLocationRecommendedDto(idInternshipLocation, locationCompanyDto, internshipDto, score, reason)
    }
}