package oportunia.maps.frontend.taskapp.data.remote.serializer

import android.util.Log
import com.google.gson.*
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RoleDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import java.lang.reflect.Type

/**
 * Custom JSON deserializer for [LocationCompanyDto] objects.
 *
 * Handles nested LatLng and Company deserialization.
 */
class LocationCompanyDeserializer : JsonDeserializer<LocationCompanyDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocationCompanyDto {

        //////
        val jsonObject = json.asJsonObject

        // Acceder al objeto locationCompany
        val id = jsonObject.get("idLocationCompany").asLong
        val latitude = jsonObject.get("latitude").asDouble
        val longitude = jsonObject.get("longitude").asDouble
        val email = jsonObject.get("email").asString
        val contact = jsonObject.get("contactLocation").asInt

        // Acceder al objeto company anidado
        val companyObject = jsonObject.getAsJsonObject("company")
        val companyId = companyObject.get("idCompany").asLong
        val companyName = companyObject.get("nameCompany").asString
        val companyDescription = companyObject.get("description").asString
        val companyHistory = companyObject.get("history").asString
        val companyMision = companyObject.get("mision").asString
        val companyVision = companyObject.get("vision").asString
        val companyCorporateCultur = companyObject.get("corporateCultur").asString
        val companyContact = companyObject.get("contactCompany").asInt
        val companyRating = companyObject.get("ratingCompany").asDouble
        val companyImageProfile = try {
            companyObject.get("imageProfile")?.asString ?: "empty"
        } catch (e: Exception) {
            Log.e("LocationCompanyDeserializer", "Error al obtener 'imageProfile': ${e.message}")
            "empty"
        }
        var companyInternshipType = companyObject.get("internshipType").asString

        if (companyInternshipType != "REM" && companyInternshipType != "INP" && companyInternshipType != "MIX") {
            companyInternshipType = "REM"
        }

        // Acceder al objeto user anidado dentro de company
        val userObject = companyObject.getAsJsonObject("user")
        val userId = userObject.get("id").asLong
        val firstName = userObject.get("firstName").asString
        val lastName = userObject.get("lastName").asString
        val emailUser = userObject.get("email").asString
        val enable = userObject.get("enabled").asBoolean
        val tokenExpired = userObject.get("tokenExpired").asBoolean
        val createDate = userObject.get("createDate").asString
        val roleListJson = userObject.getAsJsonArray("roleList")
        val roleList: List<RoleDto> = roleListJson.map { element ->
            val roleObject = element.asJsonObject
            val idrole = roleObject.get("id").asLong
            val name = roleObject.get("name").asString

            RoleDto(id = idrole, name = name)
        }

        val userDto = UserDto(userId, emailUser, firstName, lastName, enable, tokenExpired, createDate, roleList)
        val companyDto = CompanyDto(
            companyId, companyName, companyDescription, companyHistory,
            companyMision, companyVision, companyCorporateCultur, companyContact,
            companyRating, companyInternshipType, companyImageProfile, userDto
        )

        return LocationCompanyDto(id, email, latitude, longitude, contact, companyDto)
    }
}