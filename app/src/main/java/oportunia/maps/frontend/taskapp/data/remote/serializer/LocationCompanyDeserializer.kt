package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.*
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
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
        val jsonObject = json.asJsonObject

        // Deserialize LocationCompanyDto fields
        val id = jsonObject.get("id").asLong
        val email = jsonObject.get("email").asString
        val contact = jsonObject.get("contact").asInt
        val latitude = jsonObject.get("latitude").asDouble
        val longitude = jsonObject.get("longitude").asDouble

        // Manually deserialize the flattened company fields
        val companyId = jsonObject.get("idCompany").asLong
        val companyName = jsonObject.get("name").asString
        val companyDescription = jsonObject.get("description").asString
        val companyHistory = jsonObject.get("history").asString
        val companyMision = jsonObject.get("mision").asString
        val companyVision = jsonObject.get("vision").asString
        val companyCorporateCultur = jsonObject.get("corporateCultur").asString
        val companyContact = jsonObject.get("contactCompany").asInt
        val companyRating = jsonObject.get("rating").asDouble
        var companyInternshipType = jsonObject.get("internshipType").asString

        if(companyInternshipType != "REM" && companyInternshipType != "INP" && companyInternshipType != "MIX"){
            companyInternshipType = "REM"
        }

        // Deserialize user information
        val userId = jsonObject.get("idUser").asLong
        val userEmail = jsonObject.get("emailUser").asString
        val userPassword = jsonObject.get("password").asString

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, userEmail, userPassword)
        val companyDto = CompanyDto(
            companyId, companyName, companyDescription, companyHistory,
            companyMision, companyVision, companyCorporateCultur, companyContact,
            companyRating, companyInternshipType, userDto
        )

        // Return the LocationCompanyDto object
        return LocationCompanyDto(id, email, latitude, longitude, contact, companyDto)
    }
}