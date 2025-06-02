package oportunia.maps.frontend.taskapp.data.remote.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RoleDto
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


        val studentId = jsonObject.get("id").asLong
        val studentName = jsonObject.get("name").asString
        val identification = jsonObject.get("identification").asInt
        val personalInfo = jsonObject.get("personalInfo").asString
        val experience = jsonObject.get("experience").asString
        val rating = jsonObject.get("rating").asDouble


        // Deserialize user information
        val userId = jsonObject.get("id").asLong
        val firstName = jsonObject.get("firstName").asString
        val lastName = jsonObject.get("lastName").asString
        val emailUser = jsonObject.get("email").asString
        val enable = jsonObject.get("enable").asBoolean
        val tokenExpired = jsonObject.get("tokenExpired").asBoolean
        val createDate = jsonObject.get("createDate").asString
        val roleListJson = jsonObject.getAsJsonArray("roleList")
        val roleList: List<RoleDto> = roleListJson.map { element ->
            val roleObject = element.asJsonObject
            val idrole = roleObject.get("id").asLong
            val namerole = roleObject.get("name").asString

            RoleDto(id = idrole, name = namerole)
        }

        // Create UserDto and CompanyDto
        val userDto = UserDto(userId, emailUser, firstName, lastName, enable, tokenExpired, createDate, roleList)

        // Return the StudentDto object
        return StudentDto(
            studentId, studentName, identification, personalInfo,
            experience, rating, userDto
        )
    }
}