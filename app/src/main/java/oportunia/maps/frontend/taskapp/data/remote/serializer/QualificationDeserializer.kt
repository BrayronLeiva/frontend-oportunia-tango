package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.CompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import oportunia.maps.frontend.taskapp.domain.model.Student
import java.lang.reflect.Type


/**
 * Custom JSON deserializer for [QualificationDto] objects.
 *
 * Handles nested LatLng and Qualification deserialization.
 */
class QualificationDeserializer : JsonDeserializer<QualificationDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): QualificationDto {
        val jsonObject = json.asJsonObject

        // Deserialize LocationCompanyDto fields
        val id = jsonObject.get("id").asLong
        val name = jsonObject.get("name").asString
        val area = jsonObject.get("area").asString


        // Manually deserialize the flattened company fields
        val studentId = jsonObject.get("idStudent").asLong
        val studentName = jsonObject.get("studentName").asString
        val identification = jsonObject.get("identification").asInt
        val personalInfo = jsonObject.get("personalInfo").asString
        val experience = jsonObject.get("experience").asString
        val rating = jsonObject.get("rating").asDouble


        // Deserialize user information
        val userId = jsonObject.get("userId").asLong
        val userEmail = jsonObject.get("userEmail").asString
        val userPassword = jsonObject.get("userPassword").asString

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, userEmail, userPassword)
        val studentDto = StudentDto(
            studentId, studentName, identification, personalInfo,
            experience, rating, userDto
        )

        // Return the LocationCompanyDto object
        return QualificationDto(id, name, area, studentDto)
    }
}