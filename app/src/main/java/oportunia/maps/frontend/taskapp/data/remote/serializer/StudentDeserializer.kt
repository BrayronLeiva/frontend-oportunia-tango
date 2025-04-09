package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import java.lang.reflect.Type


/**
 * Custom JSON deserializer for [QualificationDto] objects.
 *
 * Handles nested LatLng and Qualification deserialization.
 */
class StudentDeserializer : JsonDeserializer<StudentDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): StudentDto {
        val jsonObject = json.asJsonObject


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

        // Return the StudentDto object
        return StudentDto(
            studentId, studentName, identification, personalInfo,
            experience, rating, userDto
        )
    }
}